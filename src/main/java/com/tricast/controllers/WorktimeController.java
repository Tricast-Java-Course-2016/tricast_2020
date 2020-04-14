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
import com.tricast.managers.WorktimeManager;
import com.tricast.repositories.entities.Worktime;

@RestController
@RequestMapping(path = "rest/worktimes")
public class WorktimeController {
	
	@Autowired
	private WorktimeManager worktimeManager;
	
	
	@GetMapping(path = "/{id}")
	public List<Worktime> getAllWorktimeByWorktimeId(@PathVariable("id") long id){
		return worktimeManager.getAllWorktimeByWorktimeId(id);
	}
	
	
	@PostMapping(path = "/create")
	 WorkdayCreationResponse createWorkdayWithWorktime(@RequestBody WorkdayCreationRequest workdayCreationRequest) {
		return worktimeManager.createWorkdayWithWorktimeFromRequest(workdayCreationRequest);
	}
	
	@PutMapping(path = "/{id}")
	public List<Worktime> saveWorktimesAndModified(@RequestBody WorkTimeUpdateListRequest worktimesListRequest,@PathVariable("id") long id) {
		return worktimeManager.saveModified(worktimesListRequest,id);
	}
	
	@GetMapping(path = "/Stats/{year}/{id}")
	public WorkTimeStatByIdResponse getWorkTimesStat(@PathVariable("id") long id,@PathVariable("year") int year){
		return worktimeManager.WorkTimeStatByIdResponse(id,year);
	}
}
