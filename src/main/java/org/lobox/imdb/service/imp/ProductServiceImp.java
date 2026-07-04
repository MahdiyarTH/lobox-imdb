package org.lobox.imdb.service.imp;

import org.lobox.common.data.base.BaseServiceImp;
import org.lobox.imdb.entity.ProductEntity;
import org.lobox.imdb.repository.ProductRepository;
import org.lobox.imdb.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp
        extends BaseServiceImp<ProductEntity, Long, ProductRepository>
        implements ProductService {

    public ProductServiceImp(ProductRepository repository) {
        super(repository);
    }

}
