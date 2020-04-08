package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestBody;

import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.api.responses.WorkdayCreationResponse;
import com.tricast.api.responses.WorkdayGetResponse;
import com.tricast.repositories.entities.Workday;

public interface WorkdayManager {	
	List<WorkdayGetResponse> getAllWorkdayByUserIdAndMonth(int id);
	void deleteById(long id);
}
