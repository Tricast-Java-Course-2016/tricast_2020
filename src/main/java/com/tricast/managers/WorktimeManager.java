package com.tricast.managers;

import java.util.List;

import com.tricast.api.requests.WorkTimeUpdateListRequest;
import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.api.responses.WorkdayCreationResponse;
import com.tricast.repositories.entities.Worktime;
import com.tricast.api.responses.WorkTimeStatByIdResponse;
import com.tricast.api.responses.WorktimesUpdateResponse;

public interface WorktimeManager {
	
	List<Worktime> getAllWorktimeByWorktimeId(int loggedInUser,long workdayId) throws Exception;
    
    List<Worktime> getAllWorktimeByWorktimeId(long workdayId);
	
	WorktimesUpdateResponse saveModified(WorkTimeUpdateListRequest worktimesListRequest, long workDayid);
	
	//List<Worktime> getAllByType(WorktimeType worktype);
	
	WorkdayCreationResponse createWorkdayWithWorktimeFromRequest( WorkdayCreationRequest workdayCreationRequest);

	int deleteAllWorkTimesById(long id);

	WorkTimeStatByIdResponse workTimeStatByIdResponse(long id,int year);
}
