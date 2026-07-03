package org.lobox.imdb.service;

import org.lobox.common.data.BaseService;
import org.lobox.imdb.entity.GenreEntity;
import org.lobox.imdb.repository.GenreRepository;

public interface GenreService extends BaseService<GenreEntity, Long, GenreRepository> {
}
