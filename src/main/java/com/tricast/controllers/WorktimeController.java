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

import com.tricast.api.requests.WorkTimeUpdateListRequest;
import com.tricast.api.responses.WorkTimeStatByIdResponse;
import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.api.responses.WorkdayCreationResponse;
import com.tricast.api.responses.WorktimesUpdateResponse;
import com.tricast.managers.WorktimeManager;
import com.tricast.repositories.entities.Worktime;
import com.tricast.repositories.entities.enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;

@RestController
@RequestMapping(path = "rest/worktimes")
public class WorktimeController {
	
	@Autowired
	private WorktimeManager worktimeManager;
	
	
	@GetMapping(path = "/{workdayId}")
	public List<Worktime> getAllWorktimeByWorktimeId(@PathVariable("workdayId") long workdayId){
		return worktimeManager.getAllWorktimeByWorktimeId(workdayId);
	}
	
	
	@PostMapping(path = "/create")
	public ResponseEntity<?> createWorkdayWithWorktime(@RequestAttribute("authentication.roleId") int roleId,@RequestAttribute("authentication.userId") int loggedInUser,@RequestBody WorkdayCreationRequest workdayCreationRequest) {
		if(userCheck(roleId, loggedInUser, workdayCreationRequest.getUserId())){
            try {
                 return ResponseEntity.ok(worktimeManager.createWorkdayWithWorktimeFromRequest(workdayCreationRequest));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
        }
	}
	
	@PutMapping(path = "/{workdayId}")
	public ResponseEntity<?> saveWorktimesAndModified(@RequestAttribute("authentication.roleId") int roleId,@RequestAttribute("authentication.userId") int loggedInUser,@RequestBody WorkTimeUpdateListRequest worktimesListRequest,@PathVariable("workdayId") long workdayId) {
		if(userCheck(roleId, loggedInUser, worktimesListRequest.getUserId())){
            try {
                return ResponseEntity.ok(worktimeManager.saveModified(worktimesListRequest,workdayId));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
        }
	}
	
	@GetMapping(path = "/Stats/{year}/{userId}")
	public WorkTimeStatByIdResponse getWorkTimesStat(@PathVariable("userId") long id,@PathVariable("year") int year){
		return worktimeManager.workTimeStatByIdResponse(id,year);
	}
    
    private boolean userCheck(int roleId,int loggedInUser,int inRequestUserID){
        return Role.ADMIN == Role.getById(roleId) || loggedInUser ==inRequestUserID;
    }
}
