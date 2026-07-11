package org.lobox.imdb.repository;

import org.lobox.common.data.base.BaseRepository;
import org.lobox.imdb.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<ProductEntity, String> {

    @Query("""
                SELECT DISTINCT pr.title
                            FROM ProductEntity pr
                                JOIN ProductPersonEntity director
                                    ON director.product.tconst = pr.tconst AND director.type = 1
                                JOIN ProductPersonEntity writer
                                    ON writer.product.tconst = pr.tconst AND writer.type = 2
                                JOIN PersonEntity per
                                    ON per.id = director.person.id
                            WHERE director.person.id = writer.person.id
                                AND per.isAlive IS true
            """)
    Page<String> findAllSameWriteAndDirectorAlive(Pageable pageable);

    @Query("""
            SELECT p.title
            FROM ProductEntity p
                     JOIN ProductPersonEntity pp
                          ON pp.product.tconst = p.tconst
            WHERE pp.person.id IN (:actorId1, :actorId2)
              AND pp.type = 0
            GROUP BY p.tconst, p.title
            HAVING COUNT(DISTINCT pp.person.id) = 2
            """)
    Page<String> findAllTitlesWithTwoActors(@Param("actorId1") Long actorId1, @Param("actorId2") Long actorId2, Pageable pageable);
}
