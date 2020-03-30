package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import com.tricast.repositories.entities.OffdayLimits;

public interface OffDayLimitsManager {

	Optional<OffdayLimits> getById(long id);
	
	OffdayLimits createOffdayLimits(OffdayLimits offdayLimitsRequest);
	
	OffdayLimits updateOffdayLimits(OffdayLimits offdayLimitsRequest);
	
	void deleteOffdayLimits(long id);
	
	boolean existsOffdayLimits(long id);

	List<OffdayLimits> getAllOffdayLimits();
}
