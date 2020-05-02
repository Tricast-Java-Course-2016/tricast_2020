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
import com.tricast.managers.WorktimeManager;
import com.tricast.repositories.entities.enums.Role;

@RestController
@RequestMapping(path = "rest/worktimes")
public class WorktimeController {

    @Autowired
    private WorktimeManager worktimeManager;

    @GetMapping(path = "/{workdayId}")
    public ResponseEntity<?> getAllWorktimeByWorktimeId(@RequestAttribute("authentication.roleId") int roleId, @RequestAttribute("authentication.userId") int loggedInUser, @PathVariable("workdayId") long workdayId) throws Exception {
        try {
            if (Role.getById(roleId) == Role.ADMIN) {
                // AKOS2: itt még mindig db entityket adtok vissza
                // azokat response osztályokra célszerű lecserélni
                return ResponseEntity.ok(worktimeManager.getAllWorktimeByWorktimeId(workdayId));
            } else {
                return ResponseEntity.ok(worktimeManager.getAllWorktimeByWorktimeId(loggedInUser, workdayId));
            }
            // AKOS2: mivel nincs különbség az exception-ök lekezelése köztött itt
            // teljessen elég csak az utolsó legáltalánosabbat meghagyni (Exception)
            // nem kell külön legkezeni az IllegalAccessException és az NoSuchElementException -t
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createWorkdayWithWorktime(@RequestAttribute("authentication.roleId") int roleId, @RequestAttribute("authentication.userId") int loggedInUser, @RequestBody WorkdayCreationRequest workdayCreationRequest) {
        if (userCheck(roleId, loggedInUser, workdayCreationRequest.getUserId())) {
            try {
                return ResponseEntity.ok(worktimeManager.createWorkdayWithWorktimeFromRequest(workdayCreationRequest));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
        }
    }

    @PutMapping(path = "/{workdayId}")
    public ResponseEntity<?> saveWorktimesAndModified(@RequestAttribute("authentication.roleId") int roleId, @RequestAttribute("authentication.userId") int loggedInUser, @RequestBody WorkTimeUpdateListRequest worktimesListRequest, @PathVariable("workdayId") long workdayId) {
        if (userCheck(roleId, loggedInUser, worktimesListRequest.getUserId())) {
            try {
                return ResponseEntity.ok(worktimeManager.saveModified(worktimesListRequest, workdayId));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
        }
    }

    @GetMapping(path = "/stats/{year}/{userId}")
    public WorkTimeStatByIdResponse getWorkTimesStat(@PathVariable("userId") long id, @PathVariable("year") int year) {
        return worktimeManager.workTimeStatByIdResponse(id, year);
    }

    private boolean userCheck(int roleId, int loggedInUser, int inRequestUserID) {
        return Role.ADMIN == Role.getById(roleId) || loggedInUser == inRequestUserID;
    }
}
