package com.tricast.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tricast.managers.SpecialDayManager;
import com.tricast.repositories.entities.Specialday;

@RestController
@RequestMapping(path = "rest/specialdays")
public class SpecialDayController {
	

	@Autowired
	private SpecialDayManager specialDayManager;
	
	@GetMapping
	public List<Specialday> getSpecialDaysInTheYear(){
		return specialDayManager.getSpecialDaysInTheYear(null);
	}
	
	@GetMapping(path = "/{year}")
	public List<Specialday> getSpecialDaysInTheYear(@PathVariable("year") String yearRequest){
		return specialDayManager.getSpecialDaysInTheYear(yearRequest);
	}
	
	@PostMapping
	public Specialday saveSpecialday(@RequestBody Specialday specialdayRequest) {
		return specialDayManager.createSpecialday(specialdayRequest);
	}
	
	@DeleteMapping()
	public void deleteSpecialday(@RequestBody int id) {
		specialDayManager.deleteSpecialday(id);
	}
}
