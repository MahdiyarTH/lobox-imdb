package org.lobox.imdb.repository;

import org.lobox.common.data.base.BaseRepository;
import org.lobox.imdb.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<ProductEntity, String> {

    @Query("""
                SELECT DISTINCT tb.title
                            FROM ProductEntity tb
                                JOIN ProductPersonEntity director
                                    ON director.product.id = tb.id AND director.type = 1
                                JOIN ProductPersonEntity writer
                                    ON writer.product.id = tb.id AND writer.type = 2
                                JOIN PersonEntity per
                                    ON per.id = director.person.id
                            WHERE director.person.id = writer.person.id
                                AND per.isAlive IS true
            """)
    Page<String> findAllSameWriteAndDirectorAlive(Pageable pageable);

}
