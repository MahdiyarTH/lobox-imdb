package org.lobox.common.data;

import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class BaseServiceImp<ENTITY, ID extends Serializable, REPO extends BaseRepository<ENTITY, ID>> implements BaseService<ENTITY, ID, REPO> {

    @Override
    public ENTITY save(ENTITY entity) {
        return null;
    }

}
