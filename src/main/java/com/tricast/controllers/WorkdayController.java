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
import com.tricast.api.responses.WorkdayWorktimesGetResponse;
import com.tricast.managers.WorkdayManager;
import com.tricast.managers.WorktimeManager;
import com.tricast.repositories.entities.Workday;
import com.tricast.repositories.entities.Worktime;

@RestController
@RequestMapping(path = "rest/workdays")
public class WorkdayController {
	
	@Autowired
	private WorkdayManager workdayManager;
	
	//Delete all associated worktime element from the database
	@Autowired
	private WorktimeManager worktimeManager;
	
	@GetMapping(path = "/{id}")
	public Optional<Workday> getByWorkdayId(@PathVariable("id") long id){
		return workdayManager.getById(id);
	}
	
	@GetMapping
	public Iterable<Workday> getAllWorkday(){
		return workdayManager.getAll();
	}
	
	@GetMapping(path = "/workedhours")
	public List<WorkdayGetResponse> getAllWorkdaysByIdAndMonth(){
		return workdayManager.getAllWorkdayByUserIdAndMonth();
	}
	
	@GetMapping(path = "/workedhours/{date}")
	public WorkdayWorktimesGetResponse geWorktimesByDate(@PathVariable("date") String date) {
		return workdayManager.getUserWorktimesByDate(date);
	}
	
	@GetMapping(path = "/workedhours/stats")
	public WorkdayStatsResponse getWorkdaysStats() {
		return null;
	}
	
	@PostMapping
	public Workday saveWorkday(@RequestBody Workday workdayRequest) {
		return workdayManager.createWorkday(workdayRequest);
	}
	
	@PostMapping(path = "/workedhours")
	public WorkdayCreationResponse createWorkday(@RequestBody WorkdayCreationRequest workdayCreationRequest) {
		return workdayManager.createWorkdayFromRequest(workdayCreationRequest);
	}
	
	@PutMapping(path = "/{id}")
	public Workday updateWorkdayById(@RequestBody Workday workdayRequest, @PathVariable("id") long id) {
		return workdayManager.updateWorkday(workdayRequest, id);
	}
	
	@PutMapping(path = "/workedhours/{date}")
	public WorkdayCreationResponse updateWorkdayByDate(@RequestBody WorkdayCreationRequest workdayCreationRequest) {
		
		return workdayManager.createWorkdayFromRequest(workdayCreationRequest);
	}
	
	@DeleteMapping(path = "/{id}")
	public void deleteWorkday(@PathVariable("id") long id){
		
		/*Iterable<Worktime> worktimeIterable = worktimeManager.getAll();
		
		for (Worktime curr : worktimeIterable) {
			if (id == curr.getWorkdayId()) {
				long worktimeId = curr.getId();
				worktimeManager.deleteById(worktimeId);
				System.out.println(worktimeId + " worktime element has been removed.");
			}
		}*/
		
		workdayManager.deleteById(id);
	}
	
}
