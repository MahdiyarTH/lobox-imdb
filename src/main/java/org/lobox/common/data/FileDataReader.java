package org.lobox.common.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lobox.imdb.entity.GenreEntity;
import org.lobox.imdb.entity.PersonEntity;
import org.lobox.imdb.entity.ProductEntity;
import org.lobox.imdb.entity.ProductGenreEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileDataReader {

    private final ApplicationContext applicationContext;
    private final Map<String, Long> personIdMap = new HashMap<>();

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @PersistenceContext
    private EntityManager entityManager;

    private BufferedReader principalReader = null;

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws IOException {
        FileDataReader self = (FileDataReader) applicationContext.getBean("fileDataReader");
        self.principalReader = new BufferedReader(
                new FileReader("title.principals.tsv"),
                128 * 1024
        );
        self.saveNames();
        self.saveProducts();
    }

    @Transactional
    public void saveNames() throws IOException {
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        try (
                BufferedReader reader = new BufferedReader(
                        new FileReader("name.basics.tsv"),
                        128 * 1024)
        ) {
            //for skipping header
            reader.readLine();

            String line;
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\t");
                final String nconst = split[0];
                final String fullName = split[1];
                final int deathYear = split[3].equals("\\N") ? Integer.MAX_VALUE : Integer.parseInt(split[3]);

                final PersonEntity person = new PersonEntity(
                        nconst,
                        fullName,
                        deathYear < currentYear
                );
                entityManager.persist(person);
                personIdMap.put(nconst, person.getId());
                counter++;

                if (counter % batchSize == 0) {
                    long startTime = System.currentTimeMillis();
                    entityManager.flush();
                    entityManager.clear();
                    log.info("{} crews saved in {} ms", counter, System.currentTimeMillis() - startTime);
                }

                if (counter >= 1_000_000)
                    break;
            }

            entityManager.flush();
            entityManager.clear();
        }
    }

    @Transactional
    public void saveProducts() throws IOException {
        long startTotal = System.currentTimeMillis();
        final Map<String, GenreEntity> genreMap = new HashMap<>();

        try (
                BufferedReader reader = new BufferedReader(
                        new FileReader("title.basics.tsv"),
                        128 * 1024)
        ) {
            //for skipping header
            reader.readLine();
            String line;
            AtomicInteger counter = new AtomicInteger(0);
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\t");

                final ProductEntity productEntity = ProductEntity.fromSplit(split);
                entityManager.persist(productEntity);
                counter.incrementAndGet();

                storeGenres(split, genreMap, counter, productEntity);
                storePersons(split, counter, productEntity);

                if (counter.get() % batchSize == 0) {
                    long startTime = System.currentTimeMillis();
                    entityManager.flush();
                    entityManager.clear();
                    log.info("{} products saved in {} ms", counter, System.currentTimeMillis() - startTime);
                }

                if (counter.get() >= 1_000_000)
                    break;
            }

            entityManager.flush();
            entityManager.clear();

            System.out.println("TEST: " + (System.currentTimeMillis() - startTotal));
        }

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

            if (counter.get() % batchSize == 0) {
                long startTime = System.currentTimeMillis();
                entityManager.flush();
                entityManager.clear();
                log.info("{} productGenre saved in {} ms", counter, System.currentTimeMillis() - startTime);
            }
        }

        entityManager.flush();
        entityManager.clear();
    }

    private void storePersons(String[] split,
                              AtomicInteger counter,
                              ProductEntity productEntity) {

    }
}
