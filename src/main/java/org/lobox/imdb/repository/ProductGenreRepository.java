package org.lobox.imdb.repository;

import org.lobox.common.data.BaseRepository;
import org.lobox.imdb.entity.ProductGenreEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductGenreRepository extends BaseRepository<ProductGenreEntity, Long> {
}
