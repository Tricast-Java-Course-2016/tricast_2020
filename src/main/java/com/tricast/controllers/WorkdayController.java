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

import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.api.responses.WorkdayCreationResponse;
import com.tricast.api.responses.WorkdayGetResponse;
import com.tricast.api.responses.WorkdayStatsResponse;
import com.tricast.managers.WorkdayManager;
import com.tricast.managers.WorktimeManager;
import com.tricast.repositories.entities.Workday;
import com.tricast.repositories.entities.Worktime;

@RestController
@RequestMapping(path = "rest/workdays")
public class WorkdayController {
	
	@Autowired
	private WorkdayManager workdayManager;	
	
	@GetMapping(path = "/workedhours/{id}")
	public List<WorkdayGetResponse> getAllWorkdaysByIdAndMonth(@PathVariable("id") int id){
		return workdayManager.getAllWorkdayByUserIdAndMonth(id);
	}

	
	@DeleteMapping(path = "/{id}")
	public void deleteWorkday(@PathVariable("id") long id){
		workdayManager.deleteById(id);
	}
	
}
