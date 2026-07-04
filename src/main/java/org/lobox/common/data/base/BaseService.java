package org.lobox.common.data.base;

import java.io.Serializable;
import java.util.List;

public interface BaseService<ENTITY, ID extends Serializable, REPO extends BaseRepository<ENTITY, ID>> {

    ENTITY save(ENTITY entity);

    List<ENTITY> saveAll(List<ENTITY> entities);

}
