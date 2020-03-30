package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import com.tricast.repositories.entities.Offday;

public interface OffDayManager {
	
	Optional<Offday> getById(long id);
	
	List<Offday> getAll();
	
	Offday createOffday(Offday offdayRequest);
	
	Offday modifyOffday(Offday offdaymodify);
	
	void deleteOffday(long leaveId);

	List<Offday> getAlloffDays();
}
