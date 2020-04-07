package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestBody;

import com.tricast.api.requests.WorkTimeUpdateListRequest;
import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.api.requests.WorktimeCreationRequest;
import com.tricast.api.responses.WorkdayCreationResponse;
import com.tricast.api.responses.WorktimeCreationResponse;
import com.tricast.repositories.entities.Worktime;
import com.tricast.repositories.entities.enums.WorktimeType;

public interface WorktimeManager {
	
	Iterable<Worktime> getAll();
	
	List<Worktime> saveModified(WorkTimeUpdateListRequest worktimesListRequest, long workDayid);
	
	
	WorkdayCreationResponse createWorkdayWithWorktimeFromRequest( WorkdayCreationRequest workdayCreationRequest);

}
