package com.tricast.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.tricast.repositories.entities.Offday;

public interface OffDayRepository extends CrudRepository<Offday, Long> {

	@Override
	List<Offday> findAll();
	
	Offday findbyId(Long id);
}
