package org.lobox.imdb.service.imp;

import org.lobox.common.data.base.BaseServiceImp;
import org.lobox.imdb.entity.ProductGenreEntity;
import org.lobox.imdb.repository.ProductGenreRepository;
import org.lobox.imdb.service.ProductGenreService;
import org.springframework.stereotype.Service;

@Service
public class ProductGenreServiceImp
        extends BaseServiceImp<ProductGenreEntity, Long, ProductGenreRepository>
        implements ProductGenreService {

    public ProductGenreServiceImp(ProductGenreRepository repository) {
        super(repository);
    }

}
