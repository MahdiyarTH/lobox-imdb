package org.lobox.imdb.service;

import org.lobox.common.data.base.BaseService;
import org.lobox.imdb.entity.ProductPersonEntity;
import org.lobox.imdb.repository.ProductPersonRepository;

public interface ProductPersonService
        extends BaseService<ProductPersonEntity, ProductPersonEntity.ProductPersonId, ProductPersonRepository> {
}
