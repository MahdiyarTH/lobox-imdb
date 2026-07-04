package org.lobox.common.data.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;
import java.util.List;

public class BaseRepositoryImp<ENTITY, ID extends Serializable>
        extends SimpleJpaRepository<ENTITY, ID>
        implements BaseRepository<ENTITY, ID> {

    private EntityManager entityManager;

    public BaseRepositoryImp(JpaEntityInformation<ENTITY, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    protected <S extends ENTITY> Page<S> readPage(TypedQuery<S> query,
                                                  Class<S> domainClass,
                                                  Pageable pageable,
                                                  @Nullable Specification<S> spec) {
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<S> content = query.getResultList();
        return new PageImpl<>(content, pageable, content.size());
    }

}
