package org.lobox.imdb.service.imp;

import org.lobox.common.data.base.BaseServiceImp;
import org.lobox.imdb.entity.ProductEntity;
import org.lobox.imdb.repository.ProductRepository;
import org.lobox.imdb.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp
        extends BaseServiceImp<ProductEntity, Long, ProductRepository>
        implements ProductService {

    public ProductServiceImp(ProductRepository repository) {
        super(repository);
    }

    @Override
    public Page<String> findAllSameWriteAndDirectorAlive(int page, int size) {
        return getRepository()
                .findAllSameWriteAndDirectorAlive(
                        PageRequest.of(page, size)
                );
    }
}
