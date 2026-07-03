package org.lobox.imdb.repository;

import org.lobox.common.data.BaseRepository;
import org.lobox.imdb.entity.ProductEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<ProductEntity, Long> {
}
