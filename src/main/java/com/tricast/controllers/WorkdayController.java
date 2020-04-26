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
import com.tricast.api.responses.WorkdayWithWorkHoursStatsGetResponse;
import com.tricast.managers.WorkdayManager;
import com.tricast.managers.WorktimeManager;
import com.tricast.repositories.entities.Workday;
import com.tricast.repositories.entities.Worktime;
import com.tricast.repositories.entities.enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;

@RestController
@RequestMapping(path = "rest/workdays")
public class WorkdayController {
	
	@Autowired
	private WorkdayManager workdayManager;	
	
	@GetMapping(path = "/workedhours/{userId}")
	public ResponseEntity<?> getAllWorkdaysByIdAndMonth(@RequestAttribute("authentication.roleId")int roleId ,@PathVariable("userId") int userId){
        try {
            return ResponseEntity.ok(workdayManager.getAllWorkdayByUserIdAndMonth(userId,roleId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}

	
	@DeleteMapping(path = "/{wordayId}")
	public ResponseEntity<?> deleteWorkday(@PathVariable("wordayId") long wordayId){
        try {
            workdayManager.deleteById(wordayId);
            return ResponseEntity.ok("DELETE SUCCESSFUL");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}
	
}
