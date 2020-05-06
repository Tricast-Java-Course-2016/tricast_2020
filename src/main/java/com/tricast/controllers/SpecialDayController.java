package com.tricast.controllers;

import java.util.List;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tricast.api.requests.SpecialDayUpdateRequest;
import com.tricast.api.responses.SpecialDayResponse;
import com.tricast.managers.SpecialDayManager;
//import com.tricast.repositories.entities.enums.Role;

@RestController
@RequestMapping(path = "rest/specialdays")
public class SpecialDayController {

	//private static final Logger LOG = LogManager.getLogger(SpecialDayController.class);

	@Autowired
	private SpecialDayManager specialDayManager;

	@GetMapping(path = "/all")
	public List<SpecialDayResponse> getAllSpecialdays() {
		return specialDayManager.getAllSpecialDay();
	}

	/*@GetMapping
	public List<SpecialDayResponse> getSpecialDaysInTheYear() {
		return specialDayManager.getSpecialDaysInTheYear(null);
	}*/

	@GetMapping(path = "/{year}")
	public List<SpecialDayResponse> getSpecialDaysInTheYear(@PathVariable("year") String yearRequest) {
		// ORSI
		// Itt a Specialday entity helyett a SpecialDayResponse class-t kéne visszaadni
		return specialDayManager.getSpecialDaysInTheYear(yearRequest);
	}

	@PostMapping
	public SpecialDayResponse saveSpecialday(@RequestBody SpecialDayUpdateRequest specialdayRequest) {
		return specialDayManager.createSpecialday(specialdayRequest);
	}

	/*
	 * @PostMapping public ResponseEntity<?>
	 * saveSpecialday(@RequestAttribute("authentication.roleId") int roleId,
	 * 
	 * @RequestBody SpecialDayUpdateRequest specialDayUpdateRequest){
	 * LOG.info("SpecialDayUpdateRequest:" + specialDayUpdateRequest.toString());
	 * if(Role.getById(roleId) != Role.ADMIN) { return
	 * ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied"); }
	 * 
	 * return
	 * ResponseEntity.ok(specialDayManager.createSpecialday(specialDayUpdateRequest)
	 * );
	 * 
	 * }
	 */

	@DeleteMapping(path = "/{id}")
	public void deleteSpecialday(@PathVariable("id") int id) {
		specialDayManager.deleteSpecialday(id);
	}
}
