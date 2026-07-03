package org.lobox.imdb.repository;

import org.lobox.common.data.BaseRepository;
import org.lobox.imdb.entity.GenreEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends BaseRepository<GenreEntity, Long> {
}
