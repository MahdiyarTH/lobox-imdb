package org.lobox.imdb.service;

import org.lobox.common.data.base.BaseService;
import org.lobox.imdb.entity.ProductEntity;
import org.lobox.imdb.repository.ProductRepository;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService extends BaseService<ProductEntity, Long, ProductRepository> {

    Page<String> findAllSameWriteAndDirectorAlive(int page, int size);

}
