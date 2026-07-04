package org.lobox.imdb.repository;

import org.lobox.common.data.base.BaseRepository;
import org.lobox.imdb.entity.CrewEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewRepository extends BaseRepository<CrewEntity, Long> {
}
