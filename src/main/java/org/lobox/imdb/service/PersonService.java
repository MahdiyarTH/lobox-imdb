package org.lobox.imdb.service;

import org.lobox.common.data.base.BaseService;
import org.lobox.imdb.entity.PersonEntity;
import org.lobox.imdb.repository.PersonRepository;

import java.util.Map;

public interface PersonService extends BaseService<PersonEntity, Long, PersonRepository> {

    Map<Long, String> getAllIdsAndNconst();

}
