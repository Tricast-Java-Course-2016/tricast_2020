package com.tricast.managers;

import java.util.List;

import com.tricast.api.requests.WorkTimeUpdateListRequest;
import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.api.responses.WorkdayCreationResponse;
import com.tricast.repositories.entities.Worktime;
import com.tricast.api.responses.WorkTimeStatByIdResponse;

public interface WorktimeManager {
	
	List<Worktime> getAllWorktimeByWorktimeId(long id);
	
	List<Worktime> saveModified(WorkTimeUpdateListRequest worktimesListRequest, long workDayid);
	
	//List<Worktime> getAllByType(WorktimeType worktype);
	
	WorkdayCreationResponse createWorkdayWithWorktimeFromRequest( WorkdayCreationRequest workdayCreationRequest);

	void deleteAllWorkTimesById(long id);

	WorkTimeStatByIdResponse WorkTimeStatByIdResponse(long id,int year);
}
