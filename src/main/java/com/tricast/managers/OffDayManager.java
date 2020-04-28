package com.tricast.managers;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.tricast.api.responses.OffDayResponse;
import com.tricast.api.responses.WorkdayCreationResponse;
import com.tricast.api.requests.OffDayRequest;
import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.repositories.entities.Offday;
import com.tricast.repositories.entities.Worktime;

public interface OffDayManager {
	
	Optional<Offday> getById(long id);
	
	List<Offday> getAllOffDayByOffDayId(int loggedInUser,long offdayId) throws Exception;
	
	List<Offday> getAllOffDayByOffDayId(long offdayID);
	
	Offday createOffday(OffDayRequest offdayRequest);
	
	OffDayResponse createOffDayRequest(Offday offdayRequest);
		
	void deleteOffday(long leaveId);

	List<Offday> getAlloffDays();
}
