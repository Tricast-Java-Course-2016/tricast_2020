package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import com.tricast.repositories.entities.Offday;

public interface OffDayManager {
	
	Optional<Offday> getById(long id);
	
	Offday createOffday(Offday offdayRequest);
	
	Offday updateOffday(Offday offdayRequest);
	
	void deleteOffday(long leaveId);

	List<Offday> getAlloffDays();
}
