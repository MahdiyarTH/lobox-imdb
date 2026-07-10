package org.lobox.imdb.service.imp;

import org.lobox.common.data.base.BaseServiceImp;
import org.lobox.imdb.entity.ProductPersonEntity;
import org.lobox.imdb.repository.ProductPersonRepository;
import org.lobox.imdb.service.ProductPersonService;
import org.springframework.stereotype.Service;

@Service
public class ProductPersonServiceImp
        extends BaseServiceImp<ProductPersonEntity, ProductPersonEntity.ProductPersonId, ProductPersonRepository>
        implements ProductPersonService {

    public ProductPersonServiceImp(ProductPersonRepository repository) {
        super(repository);
    }

}
