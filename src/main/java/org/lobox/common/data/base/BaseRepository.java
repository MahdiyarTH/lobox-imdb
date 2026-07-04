package org.lobox.common.data.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<ENTITY, ID extends Serializable> extends JpaRepository<ENTITY, ID> {

}
