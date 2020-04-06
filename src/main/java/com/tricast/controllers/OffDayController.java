package com.tricast.controllers;

import java.sql.SQLException;
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

import com.tricast.api.responses.OffDayResponse;
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
	
	@GetMapping("/{id}")
	public Optional<Offday> getById(@PathVariable("id") long id) {
		return offdayManager.getById(id);
	}

	@PostMapping
	public Offday createOffday(@RequestBody Offday offdayRequest) throws SQLException {
		return offdayManager.createOffday(offdayRequest);
	}

	@PutMapping
	public Offday updateOffday(@RequestBody Offday offdayRequest ) {
		return offdayManager.updateOffday(offdayRequest);
	}

	@DeleteMapping("/{id}")
	public Boolean deleteOffday(@PathVariable("id") long id) {
		offdayManager.deleteOffday(id);
		return true;
	}
	
}
