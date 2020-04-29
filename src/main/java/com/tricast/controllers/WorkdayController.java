package com.tricast.controllers;

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
import com.tricast.managers.WorkdayManager;
import com.tricast.managers.exceptions.WorkingHoursException;
import com.tricast.repositories.entities.enums.Role;

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
                // AKOS2: ebben az esetben nem adnám vissza a stack trace-t (body(e.getMessage()))
                // Normál esetben a web fejlesztőnek nem mond semmit, ha valami bug miatt elszáll,
                // a felhasználónak még annyira se.
                // Viszont ha most csak kivennéd akkor soha senki nem tudná utólag kideríteni mi okozta a hibát.
                // Ezért célszerű loggolást betenni ide, hogy te az eclipse consolbana lásd mi volt a hiba,
                // valamint, hogy a szerveren a log fájlokba is belekerüljön ha esetleg már csak ott jön elő valami és
                // ha valaki nekiáll kijavítani a hibát lássa mi is az.
                // LOG.error("Failed to load worked hours: ", e);
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

    // AKOS2: láttam, hogy ugyan ez a metódus megvan a másik Controlleretekben
    // mivel elég általánossan használható kód, akármelyik Controllernek jól jöhet
    // célszerű az ilyeneket valami külön helper osztályba (pl. PermissionValidator vagy nagyon általános
    // ControllerHelper nevű osztályba) kiszervezni és akkor csak egy helyen lenne meg, mindenki onnét használhatná.
    private boolean userCheck(int roleId, int loggedInUser, int inRequestUserID) {
        return Role.ADMIN == Role.getById(roleId) || loggedInUser == inRequestUserID;
    }

}
