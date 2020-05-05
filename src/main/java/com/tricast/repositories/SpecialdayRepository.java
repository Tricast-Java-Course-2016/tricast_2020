package com.tricast.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tricast.repositories.entities.Specialday;

public interface SpecialdayRepository extends CrudRepository<Specialday, Long> {

	@Override
	List<Specialday> findAll();

	List<Specialday> getAllByDateBetween(ZonedDateTime startDateTime, ZonedDateTime finishDate);
}
