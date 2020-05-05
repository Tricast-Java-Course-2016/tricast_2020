package com.tricast.controllers;

import com.tricast.api.requests.WorkdayByMounthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tricast.controllers.constants.WorkingHoursConstants;
import static com.tricast.controllers.customClasses.ControllerHelper.userCheckValidator;
import com.tricast.managers.WorkdayManager;
import com.tricast.managers.exceptions.WorkingHoursException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "rest/workdays")
public class WorkdayController {

     private static final Logger LOG = LogManager.getLogger(WorkdayController.class);
    
    @Autowired
    private WorkdayManager workdayManager;

    @GetMapping(path = "/workedhours/{userId}")
    public ResponseEntity<?> getAllWorkdaysById(@RequestAttribute("authentication.roleId") int roleId, @RequestAttribute("authentication.userId") int loggedUserId, @PathVariable("userId") int userId) {
        if (userCheckValidator(roleId, loggedUserId, userId)) {
            try {
                return ResponseEntity.ok(workdayManager.getAllWorkdayByUserId(userId, roleId));
            } catch (Exception e) {
                LOG.info("getAllWorkdaysByIdAndMonth: Failed to load worked hours: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to load worked hours");
            }
        }
        else{
            LOG.info("getAllWorkdaysByIdAndMonth:" + "IllegalAccess, "+ "logged in user ID: "+loggedUserId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
        }
    }
    
    @PostMapping(path = "/workedhours/{userId}")
    public ResponseEntity<?> getAllWorkdaysByIdAndMonth(@RequestAttribute("authentication.roleId") int roleId, @RequestAttribute("authentication.userId") int loggedUserId, @PathVariable("userId") int userId,@RequestBody WorkdayByMounthRequest workdayByMounthRequest) {
        if (userCheckValidator(roleId, loggedUserId, userId)) {
            try {
                return ResponseEntity.ok(workdayManager.getAllWorkdayByUserIdAndMonth(userId, roleId,workdayByMounthRequest));
            } catch (Exception e) {
                LOG.info("getAllWorkdaysByIdAndMonth: Failed to load worked hours: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to load worked hours");
            }
        }
        else{
            LOG.info("getAllWorkdaysByIdAndMonth:" + "IllegalAccess, "+ "logged in user ID: "+loggedUserId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
        }
    }

    @DeleteMapping(path = "/{wordayId}")
    public ResponseEntity<?> deleteWorkday(@PathVariable("wordayId") long wordayId) throws WorkingHoursException {
        try {
            workdayManager.deleteById(wordayId);
            return ResponseEntity.ok("DELETE SUCCESSFUL");
        }catch (EmptyResultDataAccessException e) {
            LOG.info("deleteWorkday: not exists work id: " + wordayId);
            return ResponseEntity.status(WorkingHoursConstants.APPLICATION_ERROR_RESPONSE_CODE).body("Not exists work id: " + wordayId);
        }catch (Exception e) {
            LOG.info("Failed to detele workday" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to detele workday");
        }
    }
}
