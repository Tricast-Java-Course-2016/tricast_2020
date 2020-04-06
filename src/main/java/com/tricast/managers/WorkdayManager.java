package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestBody;

import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.api.responses.WorkdayCreationResponse;
import com.tricast.api.responses.WorkdayGetResponse;
import com.tricast.api.responses.WorkdayWorktimesGetResponse;
import com.tricast.repositories.entities.Workday;

public interface WorkdayManager {
	Optional<Workday> getById (long id);
	
	Iterable<Workday> getAll();
	
	Workday createWorkday(Workday workdayRequest);
	
	Workday updateWorkday(Workday workdayRequest, long id);
	
	WorkdayCreationResponse updateUserWorkdayByDate(WorkdayCreationRequest workdayCreationRequest, String day);
	
	List<WorkdayGetResponse> getAllWorkdayByUserIdAndMonth();
	
	WorkdayWorktimesGetResponse getUserWorktimesByDate(String date);
	
	WorkdayCreationResponse createWorkdayFromRequest(WorkdayCreationRequest workdayCreationRequest);
	
	void deleteById(long id);
}
