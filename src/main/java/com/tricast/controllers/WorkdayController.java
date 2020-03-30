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

import com.tricast.managers.WorkdayManager;
import com.tricast.managers.WorktimeManager;
import com.tricast.repositories.entities.Workday;
import com.tricast.repositories.entities.Worktime;

@RestController
@RequestMapping(path = "workdays")
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
	
	@PostMapping
	public Workday saveWorkday(@RequestBody Workday workdayRequest) {
		return workdayManager.createWorkday(workdayRequest);
	}
	
	@PutMapping
	public Workday saveWorkdayById(@RequestBody Workday workdayRequest) {
		return workdayManager.createWorkday(workdayRequest);
	}
	
	@DeleteMapping(path = "/{id}")
	public void deleteWorkday(@PathVariable("id") long id){
		
		Iterable<Worktime> worktimeIterable = worktimeManager.getAll();
		
		for (Worktime curr : worktimeIterable) {
			if (id == curr.getWorkdayId()) {
				long worktimeId = curr.getId();
				worktimeManager.deleteById(worktimeId);
				System.out.println(worktimeId + " worktime element has been removed.");
			}
		}
		
		workdayManager.deleteById(id);
	}
	
}
