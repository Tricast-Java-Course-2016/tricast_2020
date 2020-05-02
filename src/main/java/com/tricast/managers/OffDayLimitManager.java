package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import com.tricast.repositories.entities.OffDayLimit;

public interface OffDayLimitManager {

	Optional<OffDayLimit> getById(long id);
	
	OffDayLimit createOffDayLimit(OffDayLimit offdayLimitRequest);
	
	OffDayLimit updateOffDayLimit(OffDayLimit offdayLimitRequest);
	
	void deleteOffDayLimit(long id);
	
	boolean existsOffDayLimit(long id);

	List<OffDayLimit> getAllOffDayLimits();
}
