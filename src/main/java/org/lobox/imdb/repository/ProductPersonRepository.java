package org.lobox.imdb.repository;

import org.lobox.common.data.base.BaseRepository;
import org.lobox.imdb.entity.ProductPersonEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPersonRepository extends BaseRepository<ProductPersonEntity, ProductPersonEntity.ProductPersonId> {
}
