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

import com.tricast.managers.WorktimeManager;
import com.tricast.repositories.entities.Worktime;
import com.tricast.repositories.entities.enums.WorktimeType;

@RestController
@RequestMapping(path = "worktimes")
public class WorktimeController {
	
	@Autowired
	private WorktimeManager worktimeManager;
	
	@GetMapping(path = "/{id}")
	public Optional<Worktime> getWorktimeById(@PathVariable("id") long id){
		return worktimeManager.getById(id);
	}
	
	@GetMapping
	public Iterable<Worktime> getAllWorktime(){
		return worktimeManager.getAll();
	}
	
	@PostMapping
	public Worktime saveWorktime(@RequestBody Worktime worktimeRequest) {
		return worktimeManager.createWorktime(worktimeRequest);
	}
	
	@PutMapping
	public Worktime saveWorktimeById(@RequestBody Worktime worktimeRequest) {
		return worktimeManager.createWorktime(worktimeRequest);
	}
	 
	@DeleteMapping(path = "/{id}")
	public void deleteWorktime(@PathVariable("id") long id) {
		worktimeManager.deleteById(id);
	}
}