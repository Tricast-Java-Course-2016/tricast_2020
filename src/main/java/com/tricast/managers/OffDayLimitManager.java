package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import com.tricast.api.requests.OffDayLimitCreationRequest;
import com.tricast.api.responses.OffDayLimitCreationResponse;
import com.tricast.repositories.entities.OffDayLimit;

public interface OffDayLimitManager {

	Optional<OffDayLimit> getById(long id);
	
	OffDayLimit createOffDayLimit(OffDayLimit offdayLimitRequest);
	
	OffDayLimitCreationResponse createOffDayFromRequest(OffDayLimitCreationRequest offDayLimitCreationRequest);
	
	OffDayLimit updateOffDayLimit(OffDayLimit offdayLimitRequest);
	
	void deleteOffDayLimit(long id);
	
	boolean existsOffDayLimit(long id);

	List<OffDayLimit> getAllOffDayLimits();
}
