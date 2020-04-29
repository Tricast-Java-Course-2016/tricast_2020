package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import com.tricast.api.requests.OffDayRequest;
import com.tricast.api.responses.OffDayResponse;
import com.tricast.repositories.entities.Offday;

public interface OffDayManager {

	Optional<Offday> getById(long id);

    List<OffDayResponse> getAllOffDayByOffDayId(long offdayId) throws Exception;

    List<OffDayResponse> getAllOffDayByOffDayId(int loggedInUser, long offdayId) throws Exception;

    OffDayResponse createOffDayRequest(OffDayRequest offdayRequest);

	void deleteOffday(long leaveId);

	List<Offday> getAlloffDays();
}
