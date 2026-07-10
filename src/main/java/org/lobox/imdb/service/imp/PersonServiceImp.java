package org.lobox.imdb.service.imp;

import org.lobox.common.data.base.BaseServiceImp;
import org.lobox.imdb.entity.PersonEntity;
import org.lobox.imdb.repository.PersonRepository;
import org.lobox.imdb.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PersonServiceImp
        extends BaseServiceImp<PersonEntity, Long, PersonRepository>
        implements PersonService {

    public PersonServiceImp(PersonRepository repository) {
        super(repository);
    }

    @Override
    public Map<Long, String> getAllIdsAndNconst() {
        return getRepository()
                .getIdsAndNconst()
                .stream()
                .collect(
                        Collectors.toMap(
                                row -> (long) row[0],
                                row -> (String) row[1]
                        )
                );
    }
}
