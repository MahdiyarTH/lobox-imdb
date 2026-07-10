package org.lobox.imdb.repository;

import org.lobox.common.data.base.BaseRepository;
import org.lobox.imdb.entity.PersonEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends BaseRepository<PersonEntity, Long> {
}
