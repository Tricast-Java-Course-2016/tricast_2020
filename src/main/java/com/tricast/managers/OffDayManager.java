package com.tricast.managers;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.tricast.api.responses.OffDayResponse;
import com.tricast.api.requests.OffDayRequest;
import com.tricast.repositories.entities.Offday;

public interface OffDayManager {
	
	Optional<Offday> getById(long id);
	
	Offday createOffday(Offday OffDayRequest) throws SQLException;
	
	Offday updateOffday(Offday OffDayRequest);
	
	void deleteOffday(long leaveId);

	List<Offday> getAlloffDays();
}
