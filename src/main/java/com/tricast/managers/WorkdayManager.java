package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import com.tricast.repositories.entities.Workday;

public interface WorkdayManager {
	Optional<Workday> getById (long id);
	
	Iterable<Workday> getAll();
	
	Workday createWorkday(Workday workdayRequest);
	
	void deleteById(long id);
}
