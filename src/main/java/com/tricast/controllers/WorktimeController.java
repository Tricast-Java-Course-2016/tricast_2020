package com.tricast.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tricast.api.requests.WorkTimeUpdateListRequest;
import com.tricast.api.responses.WorkTimeStatByIdResponse;
import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.api.responses.WorkdayCreationResponse;
import com.tricast.api.responses.WorktimesUpdateResponse;
import com.tricast.managers.WorktimeManager;
import com.tricast.repositories.entities.Worktime;

@RestController
@RequestMapping(path = "rest/worktimes")
public class WorktimeController {
	
	@Autowired
	private WorktimeManager worktimeManager;
	
	
	@GetMapping(path = "/{workdayId}")
	public List<Worktime> getAllWorktimeByWorktimeId(@PathVariable("workdayId") long workdayId){
		return worktimeManager.getAllWorktimeByWorktimeId(workdayId);
	}
	
	
	@PostMapping(path = "/create")
	 WorkdayCreationResponse createWorkdayWithWorktime(@RequestBody WorkdayCreationRequest workdayCreationRequest) {
		return worktimeManager.createWorkdayWithWorktimeFromRequest(workdayCreationRequest);
	}
	
	@PutMapping(path = "/{workdayId}")
	public WorktimesUpdateResponse saveWorktimesAndModified(@RequestBody WorkTimeUpdateListRequest worktimesListRequest,@PathVariable("workdayId") long workdayId) {
		return worktimeManager.saveModified(worktimesListRequest,workdayId);
	}
	
	@GetMapping(path = "/Stats/{year}/{userId}")
	public WorkTimeStatByIdResponse getWorkTimesStat(@PathVariable("userId") long id,@PathVariable("year") int year){
		return worktimeManager.workTimeStatByIdResponse(id,year);
	}
}
