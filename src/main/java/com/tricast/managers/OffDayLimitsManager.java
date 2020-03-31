package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import com.tricast.repositories.entities.OffDayLimits;

public interface OffDayLimitsManager {

	Optional<OffDayLimits> getById(long id);
	
	OffDayLimits createOffdayLimits(OffDayLimits offdayLimitsRequest);
	
	OffDayLimits updateOffdayLimits(OffDayLimits offdayLimitsRequest);
	
	void deleteOffdayLimits(long id);
	
	boolean existsOffdayLimits(long id);

	List<OffDayLimits> getAllOffdayLimits();
}
