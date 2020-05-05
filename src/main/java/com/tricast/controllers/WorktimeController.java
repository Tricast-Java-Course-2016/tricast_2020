package com.tricast.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tricast.api.requests.WorkTimeUpdateListRequest;
import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.api.responses.WorkTimeStatByIdResponse;
import com.tricast.controllers.constants.WorkingHoursConstants;
import static com.tricast.controllers.customClasses.ControllerHelper.userCheckValidator;
import com.tricast.managers.WorktimeManager;
import com.tricast.repositories.entities.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping(path = "rest/worktimes")
public class WorktimeController {

    private static final Logger LOG = LogManager.getLogger(WorktimeController.class);
    
    @Autowired
    private WorktimeManager worktimeManager;

    @GetMapping(path = "/{workdayId}")
    public ResponseEntity<?> getAllWorktimeByWorktimeId(@RequestAttribute("authentication.roleId") int roleId, @RequestAttribute("authentication.userId") int loggedInUser, @PathVariable("workdayId") long workdayId) throws Exception {
        try {
            if (Role.getById(roleId) == Role.ADMIN) {
                return ResponseEntity.ok(worktimeManager.getAllWorktimeByWorktimeId(workdayId));
            } else {
                return ResponseEntity.ok(worktimeManager.getAllWorktimeByWorktimeId(loggedInUser, workdayId));
            }
        } catch (IllegalAccessException e) {
            LOG.info("getAllWorktimeByWorktimeId: "+ e.getMessage()+ "LoggedInuser: "+loggedInUser);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        catch (NoSuchElementException e) {
            LOG.info("getAllWorktimeByWorktimeId: " + e.getMessage()+"Searched id: "+workdayId );
            return ResponseEntity.status(WorkingHoursConstants.APPLICATION_ERROR_RESPONSE_CODE).body(workdayId+" "+"not exists");
        }
        catch (Exception e) {
            LOG.info("getAllWorktimeByWorktimeId: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to load worktimes");
        }
    }

    @PostMapping()
    public ResponseEntity<?> createWorkdayWithWorktime(@RequestAttribute("authentication.roleId") int roleId, @RequestAttribute("authentication.userId") int loggedInUser, @RequestBody WorkdayCreationRequest workdayCreationRequest) {
        if (userCheckValidator(roleId, loggedInUser, workdayCreationRequest.getUserId())) {
            try {
                return ResponseEntity.ok(worktimeManager.createWorkdayWithWorktimeFromRequest(workdayCreationRequest));
            } catch (Exception e) {
                LOG.info("createWorkdayWithWorktime: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to created worktime");
            }
        } else {
            LOG.info("createWorkdayWithWorktime:" + "IllegalAccess, "+ "logged in user: "+loggedInUser);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
        }
    }

    @PutMapping(path = "/{workdayId}")
    public ResponseEntity<?> saveWorktimesAndModified(@RequestAttribute("authentication.roleId") int roleId, @RequestAttribute("authentication.userId") int loggedInUser, @RequestBody WorkTimeUpdateListRequest worktimesListRequest, @PathVariable("workdayId") long workdayId) {
        if (userCheckValidator(roleId, loggedInUser, worktimesListRequest.getUserId())) {
            try {
                return ResponseEntity.ok(worktimeManager.saveModified(worktimesListRequest, workdayId));
            } catch (Exception e) {
                LOG.info("saveWorktimesAndModified: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to modified");
            }
        } else {
            LOG.info("saveWorktimesAndModified:" + "IllegalAccess, "+ "logged in user: "+loggedInUser);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
        }
    }

    @GetMapping(path = "/stats/{year}/{userId}")
    public WorkTimeStatByIdResponse getWorkTimesStat(@PathVariable("userId") long id, @PathVariable("year") int year) {
        return worktimeManager.workTimeStatByIdResponse(id, year);
    }
}
