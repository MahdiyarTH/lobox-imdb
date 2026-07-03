package org.lobox.common.data;

import java.io.Serializable;

public interface BaseService<ENTITY, ID extends Serializable, REPO extends BaseRepository<ENTITY, ID>> {

    ENTITY save(ENTITY entity);

}
