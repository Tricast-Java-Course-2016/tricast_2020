/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tricast.controllers.customClasses;

import com.tricast.repositories.entities.enums.Role;

/**
 *
 * @author Dell
 */
public class ControllerHelper {
     public static boolean userCheckValidator(int roleId, int loggedInUser, int inRequestUserID) {
        return Role.ADMIN == Role.getById(roleId) || loggedInUser == inRequestUserID;
    }
}
