package org.lobox.imdb.repository;

import org.lobox.common.data.base.BaseRepository;
import org.lobox.imdb.entity.ProductGenreEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductGenreRepository extends BaseRepository<ProductGenreEntity, Long> {
}
