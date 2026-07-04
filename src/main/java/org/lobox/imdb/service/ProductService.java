package org.lobox.imdb.service;

import org.lobox.common.data.base.BaseService;
import org.lobox.imdb.entity.ProductEntity;
import org.lobox.imdb.repository.ProductRepository;

public interface ProductService extends BaseService<ProductEntity, Long, ProductRepository> {
}
