package com.tricast.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tricast.repositories.entities.Worktime;
import com.tricast.repositories.entities.enums.WorktimeType;

public interface WorktimeRepository extends CrudRepository<Worktime, Long> {
	List<Worktime> findByType(WorktimeType worktimeType);
	List<Worktime> findAllByWorkdayId(long id);
	List<Worktime> findAllByWorkdayIdIn(List<Long> id);
}
