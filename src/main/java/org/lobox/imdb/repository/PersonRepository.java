package org.lobox.imdb.repository;

import org.lobox.common.data.base.BaseRepository;
import org.lobox.imdb.entity.PersonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends BaseRepository<PersonEntity, Long> {

    @Query("select p.id, p.nconst from PersonEntity p")
    List<Object[]> getIdsAndNconst();

}
