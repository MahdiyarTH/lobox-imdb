package org.lobox.imdb.service.imp;

import org.lobox.common.data.base.BaseServiceImp;
import org.lobox.imdb.entity.PersonEntity;
import org.lobox.imdb.repository.PersonRepository;
import org.lobox.imdb.service.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImp
        extends BaseServiceImp<PersonEntity, Long, PersonRepository>
        implements PersonService {

    public PersonServiceImp(PersonRepository repository) {
        super(repository);
    }

}
