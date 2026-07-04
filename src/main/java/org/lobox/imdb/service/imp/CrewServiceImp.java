package org.lobox.imdb.service.imp;

import org.lobox.common.data.base.BaseServiceImp;
import org.lobox.imdb.entity.CrewEntity;
import org.lobox.imdb.repository.CrewRepository;
import org.lobox.imdb.service.CrewService;
import org.springframework.stereotype.Service;

@Service
public class CrewServiceImp
        extends BaseServiceImp<CrewEntity, Long, CrewRepository>
        implements CrewService {

    public CrewServiceImp(CrewRepository repository) {
        super(repository);
    }

}
