package com.tricast.controllers;

import com.tricast.controllers.constants.WorkingHoursConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tricast.managers.WorkdayManager;
import com.tricast.managers.exceptions.WorkingHoursException;
import com.tricast.repositories.entities.enums.Role;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;

@RestController
@RequestMapping(path = "rest/workdays")
public class WorkdayController {

    @Autowired
    private WorkdayManager workdayManager;

    @GetMapping(path = "/workedhours/{userId}")
    public ResponseEntity<?> getAllWorkdaysByIdAndMonth(@RequestAttribute("authentication.roleId") int roleId, @RequestAttribute("authentication.userId") int loggedUserId, @PathVariable("userId") int userId) {
        if (userCheck(roleId, loggedUserId, userId)) {
            try {
                return ResponseEntity.ok(workdayManager.getAllWorkdayByUserIdAndMonth(userId, roleId));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
        }
    }

    @DeleteMapping(path = "/{wordayId}")
    public ResponseEntity<?> deleteWorkday(@PathVariable("wordayId") long wordayId) throws WorkingHoursException {
        try {
            workdayManager.deleteById(wordayId);
            return ResponseEntity.ok("DELETE SUCCESSFUL");
        }catch (EmptyResultDataAccessException e) {
             return ResponseEntity.status(WorkingHoursConstants.APPLICATION_ERROR_RESPONSE_CODE).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
        }
    }

    private boolean userCheck(int roleId, int loggedInUser, int inRequestUserID) {
        return Role.ADMIN == Role.getById(roleId) || loggedInUser == inRequestUserID;
    }

}
