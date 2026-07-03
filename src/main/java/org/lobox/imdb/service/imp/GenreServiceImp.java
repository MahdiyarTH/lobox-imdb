package org.lobox.imdb.service.imp;

import org.lobox.common.data.BaseServiceImp;
import org.lobox.imdb.entity.GenreEntity;
import org.lobox.imdb.repository.GenreRepository;
import org.lobox.imdb.service.GenreService;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImp
        extends BaseServiceImp<GenreEntity, Long, GenreRepository>
        implements GenreService {
}
