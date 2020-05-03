package com.tricast.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tricast.api.requests.OffDayLimitCreationRequest;
import com.tricast.api.requests.OffDayRequest;
import com.tricast.api.responses.OffDayLimitCreationResponse;
import com.tricast.api.responses.OffDayResponse;
import com.tricast.managers.OffDayManager;
import com.tricast.repositories.entities.OffDayLimit;
import com.tricast.repositories.entities.Offday;
import com.tricast.repositories.entities.enums.Role;

@RestController
@RequestMapping(path = "rest/offdays")
public class OffDayController {

	@Autowired
	private OffDayManager offdayManager;

	@GetMapping
    public List<OffDayResponse> getAllOffDayByOffDayId(@RequestAttribute("authentication.roleId") int roleId,
            @RequestAttribute("authentication.userId") int loggedInUser, @PathVariable("workdayId") long offdayID)
            throws Exception {
		if(Role.getById(roleId) == Role.ADMIN){
            return offdayManager.getAllOffDayByOffDayId(offdayID);
        }
        else{
            try {
                return offdayManager.getAllOffDayByOffDayId(loggedInUser,offdayID);
            } catch (Exception e) {
                throw e;
            }
        }
	}

	@PostMapping
	public ResponseEntity<?> createOffday(@RequestAttribute("authentication.roleId") int roleId,@RequestAttribute("authentication.userId") int loggedInUser,@RequestBody OffDayRequest offdayCreationRequest) {
		if(userCheck(roleId, loggedInUser, offdayCreationRequest.getuserId())){
            try {
                return ResponseEntity.ok(offdayManager.createOffDayFromRequest(offdayCreationRequest));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
        }
	}

	public ResponseEntity<?> deleteOffdayById(@PathVariable("offdayId") long offdayId) {
		try {
            offdayManager.deleteOffday(offdayId);
            return ResponseEntity.ok("DELETE SUCCESSFUL");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}

	private boolean userCheck(int roleId,int loggedInUser,Long getuserId){
        return Role.ADMIN == Role.getById(roleId) || loggedInUser ==getuserId;
    }
	
	// -- ISTVÁN
	// TESTING
	@GetMapping(path = "/all")
	public List<Offday> getAllOffDays() {
		return offdayManager.getAlloffDays();
	}
	
	@PostMapping(path = "/create")
	public OffDayResponse createOffDayLimitFromRequest(@RequestBody OffDayRequest offDayRequest) {
		return offdayManager.createOffDayFromRequest(offDayRequest);
	}
}
