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

import com.tricast.managers.OffDayManager;
import com.tricast.repositories.entities.Offday;

@RestController
@RequestMapping(path = "rest/offdays")
public class OffDayController {

	@Autowired
	private OffDayManager offdayManager;
	
	@GetMapping
	public List<Offday> getoffdays() {
		return offdayManager.getAlloffDays();
	}	
	
	
	
}
