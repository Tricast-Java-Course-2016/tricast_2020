package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import com.tricast.api.requests.OffDayLimitCreationRequest;
import com.tricast.api.requests.OffDayRequest;
import com.tricast.api.responses.OffDayLimitCreationResponse;
import com.tricast.api.responses.OffDayResponse;
import com.tricast.managers.exceptions.WorkingHoursException;
import com.tricast.repositories.entities.Offday;

public interface OffDayManager {

	Optional<Offday> getById(long id);

    List<OffDayResponse> getAllOffDayByOffDayId(long offdayId) throws Exception;

    List<OffDayResponse> getAllOffDayByOffDayId(int loggedInUser, long offdayId) throws Exception;

    OffDayResponse createOffDayFromRequest(OffDayRequest offDayRequest);
    
    void deleteOffday(long leaveId);

	List<OffDayResponse> getAllOffDays();
	
	List<OffDayResponse> getAllUnApprovedOffDays() throws WorkingHoursException;;
	
	List<OffDayResponse> getAllCurrentMonthOffDays(long loggedUserId);
}
