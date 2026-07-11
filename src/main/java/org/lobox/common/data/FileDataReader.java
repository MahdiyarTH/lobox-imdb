package org.lobox.common.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lobox.imdb.entity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileDataReader {

    private final ApplicationContext applicationContext;
    private final Map<String, Long> personNconstToIdMap = new HashMap<>();

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @PersistenceContext
    private EntityManager entityManager;

    private BufferedReader productPersonReader;

    private final Set<String> validProfessions = Set.of(
            "actor",
            "actress",
            "writer",
            "director"
    );
    private final Set<ProductPersonEntity.ProductPersonId> seenProperPersonIds = new HashSet<>();

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void init() throws IOException {
        productPersonReader = new BufferedReader(new FileReader("title.principals.tsv"), 128 * 1024);
        productPersonReader.readLine();

        FileDataReader self = (FileDataReader) applicationContext.getBean("fileDataReader");
        self.savePersons();
        self.saveProducts();
        self.saveRates();

        log.info("Done");

        productPersonReader.close();
    }

    @Transactional
    public void savePersons() {
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        readFile("name.basics.tsv",
                (counter, line) -> {
                    //nconst[0]    primaryName[1]    birthYear[2]    deathYear[3]    primaryProfession[4]    knownForTitles[5]

                    String[] split = line.split("\t");
                    //writer, director or actor
                    boolean isValidPerson = Arrays.stream(split[4].split(",")).anyMatch(validProfessions::contains);
                    if (!isValidPerson)
                        return;

                    final String nconst = split[0];
                    final String fullName = split[1];
                    final int deathYear = split[3].equals("\\N") ? Integer.MAX_VALUE : Integer.parseInt(split[3]);

                    final PersonEntity person = new PersonEntity(
                            nconst,
                            fullName,
                            deathYear < currentYear
                    );
                    entityManager.persist(person);
                    personNconstToIdMap.put(nconst, person.getId());
                    counter++;

                    flushEmIfNeeded(counter, "Person");
                }
        );

        entityManager.flush();
        entityManager.clear();
    }

    @Transactional
    public void saveProducts() {
        final Map<String, GenreEntity> genreMap = new HashMap<>();
        final AtomicInteger atomicCounter = new AtomicInteger(1);
        readFile(
                "title.basics.tsv",
                (counter, line) -> {
                    String[] split = line.split("\t");

                    final ProductEntity productEntity = ProductEntity.fromSplit(split);
                    entityManager.persist(productEntity);
                    atomicCounter.incrementAndGet();

                    storeGenres(split, genreMap, atomicCounter, productEntity);
                    storeProductPersons(atomicCounter, productEntity);

                    if (counter % batchSize == 0)
                        log.info("{} products saved", counter);

                    flushEmIfNeeded(atomicCounter.get(), "Product");
                }
        );

        entityManager.flush();
        entityManager.clear();

    }

    private void storeGenres(String[] split,
                             Map<String, GenreEntity> genreMap,
                             AtomicInteger counter,
                             ProductEntity productEntity) {
        final String[] genres = split[8].split(",");
        for (final String productGenre : genres) {
            if (productGenre.equals("\\N"))
                break;

            final ProductGenreEntity productGenreEntity = new ProductGenreEntity();
            productGenreEntity.setGenre(
                    genreMap.computeIfAbsent(
                            productGenre,
                            k -> {
                                final GenreEntity genreEntity = new GenreEntity(k);
                                entityManager.persist(genreEntity);
                                counter.incrementAndGet();
                                return genreEntity;
                            }
                    )
            );
            productGenreEntity.setProduct(productEntity);
            entityManager.persist(
                    productGenreEntity
            );

            counter.incrementAndGet();
        }
    }

    private void storeProductPersons(AtomicInteger counter,
                                     ProductEntity productEntity) {
        //tconst[0]   ordering[1]   nconst[2]   category[3]   job[4]   characters[5]
        String line;
        try {
            while ((line = productPersonReader.readLine()) != null) {
                String[] split = line.split("\t");

                /*
                When this if is TRUE, the line is read already, so next product will
                miss this line, we mark and reset to this line for prevent this line missing.
                 */
                if (!split[0].equals(productEntity.getTconst())) {
                    productPersonReader.reset();
                    return;
                }
                productPersonReader.mark(2048);

                //we only care about actors, directors and writers
                final ProductPersonEntity.Type type = ProductPersonEntity.Type.fromString(split[3]);
                if (type == null)
                    continue;

                final PersonEntity person = new PersonEntity();
                person.setId(personNconstToIdMap.get(split[2]));
                if (person.getId() == null)
                    continue;

                final ProductPersonEntity.ProductPersonId id = new ProductPersonEntity.ProductPersonId(productEntity.getTconst(), person.getId(), type);
                if (seenProperPersonIds.contains(id))
                    continue;

                final ProductPersonEntity productPersonEntity = new ProductPersonEntity(
                        productEntity,
                        person,
                        type
                );

                entityManager.persist(productPersonEntity);
                seenProperPersonIds.add(id);

                counter.incrementAndGet();
                if (flushEmIfNeeded(counter.get(), "ProductPerson"))
                    seenProperPersonIds.clear();
            }
        } catch (IOException ignore) {
        }

    }

    @Transactional
    public void saveRates() {
        readFile(
                "title.ratings.tsv",
                (counter, line) -> {
                    //tconst[0]    averageRating[1]    numVotes[2]
                    String[] split = line.split("\t");

                    final ProductEntity productEntity = new ProductEntity();
                    productEntity.setTconst(split[0]);

                    entityManager.persist(
                            new ProductRatingEntity(
                                    productEntity,
                                    Float.parseFloat(split[1]),
                                    Integer.parseInt(split[2])
                            )
                    );

                    flushEmIfNeeded(counter, "ProductRating");
                }
        );
    }

    private boolean flushEmIfNeeded(int counter, String entityName) {
        if (counter % batchSize == 0) {
            entityManager.flush();
            entityManager.clear();
            log.info("{} {} saved!", counter, entityName);

            return true;
        }

        return false;
    }

    private void readFile(String fileName, BiConsumer<Integer, String> lineReader) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName), 128 * 1024)) {
            reader.readLine();

            String line;
            int counter = 0;

            while ((line = reader.readLine()) != null)
                lineReader.accept(counter++, line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
