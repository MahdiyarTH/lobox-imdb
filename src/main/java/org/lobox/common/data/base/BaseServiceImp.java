package org.lobox.common.data.base;

import lombok.Getter;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseServiceImp<ENTITY, ID extends Serializable, REPO extends BaseRepository<ENTITY, ID>> implements BaseService<ENTITY, ID, REPO> {

    @Getter
    private final REPO repository;

    private final Class<ENTITY> entityClass;

    public BaseServiceImp(REPO repository, Class<ENTITY> entityClass) {
        this.repository = repository;
        this.entityClass = entityClass;
    }

    public BaseServiceImp(REPO repository) {
        this.repository = repository;
        Type superClass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superClass;

        //noinspection unchecked
        this.entityClass = (Class<ENTITY>) parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public ENTITY save(ENTITY entity) {
        return repository.save(entity);
    }

    @Override
    public List<ENTITY> saveAll(List<ENTITY> entities) {
        return repository.saveAll(entities);
    }
}
