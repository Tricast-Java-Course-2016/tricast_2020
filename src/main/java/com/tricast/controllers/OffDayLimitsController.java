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

import com.tricast.managers.OffDayLimitsManager;
import com.tricast.repositories.entities.OffDayLimits;

@RestController
@RequestMapping(path = "rest/offdaylimits")
public class OffDayLimitsController {

	@Autowired
	private OffDayLimitsManager offDayLimitsManager;
	
	@GetMapping(path = "/{id}")
	public Optional<OffDayLimits> getById(@PathVariable("id") long id) {
		return offDayLimitsManager.getById(id);
	}
	
	@PostMapping
	public OffDayLimits createOffdayLimits(@RequestBody OffDayLimits offdayLimitsRequest) { 
		return offDayLimitsManager.createOffdayLimits(offdayLimitsRequest);
	}
	
	@PutMapping
	public OffDayLimits updateOffdayLimits(@RequestBody OffDayLimits offdayLimitsRequest) {
		return offDayLimitsManager.updateOffdayLimits(offdayLimitsRequest);
	}
	
	@DeleteMapping(path = "/{id}")
	public boolean deleteOffday(@PathVariable("id") long id) {
		offDayLimitsManager.deleteOffdayLimits(id);
		return offDayLimitsManager.existsOffdayLimits(id);
	}
	
	@GetMapping
	public List<OffDayLimits> getAllOffdayLimits() {
		return offDayLimitsManager.getAllOffdayLimits();
	}
}
