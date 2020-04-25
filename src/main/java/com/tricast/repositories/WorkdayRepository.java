package com.tricast.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tricast.repositories.entities.Workday;

public interface WorkdayRepository extends CrudRepository<Workday, Long> {
	List<Workday> findByUserIdAndDateBetween(int id,ZonedDateTime startDateTime , ZonedDateTime finishDate);
	List<Workday> findByDateBetween(ZonedDateTime startDateTime , ZonedDateTime finishDate);
}
