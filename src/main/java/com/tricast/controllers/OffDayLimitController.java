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

import com.tricast.api.requests.OffDayLimitCreationRequest;
import com.tricast.api.responses.OffDayLimitCreationResponse;
import com.tricast.managers.OffDayLimitManager;
import com.tricast.repositories.entities.OffDayLimit;

@RestController
@RequestMapping(path = "rest/offdaylimits")
public class OffDayLimitController {

	@Autowired
	private OffDayLimitManager offDayLimitManager;

	@GetMapping(path = "/{id}")
	public Optional<OffDayLimit> getById(@PathVariable("id") long id) {
		return offDayLimitManager.getById(id);
	}

	@PostMapping
	public OffDayLimit createOffDayLimit(@RequestBody OffDayLimit offdayLimitRequest) {
		return offDayLimitManager.createOffDayLimit(offdayLimitRequest);
		//return offDayLimitManager;
	}
	
	@PostMapping(path = "/create")
	public OffDayLimitCreationResponse createOffDayLimitFromRequest(@RequestBody OffDayLimitCreationRequest offDayLimitCreationRequest) {
		return offDayLimitManager.createOffDayFromRequest(offDayLimitCreationRequest);
	}

	@PutMapping
	public OffDayLimit updateOffDayLimit(@RequestBody OffDayLimit offdayLimitRequest) {
		return offDayLimitManager.updateOffDayLimit(offdayLimitRequest);
	}

	@DeleteMapping(path = "/{id}")
	public boolean deleteOffDay(@PathVariable("id") long id) {
		offDayLimitManager.deleteOffDayLimit(id);
		return offDayLimitManager.existsOffDayLimit(id);
	}

	@GetMapping
	public List<OffDayLimit> getAllOffDayLimits() {
		return offDayLimitManager.getAllOffDayLimits();
	}
}
