/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tricast.api.responses;

import com.tricast.repositories.entities.Worktime;
import java.util.LinkedList;
import java.util.List;

public class WorktimesUpdateResponse {

    private List<Worktime> updatedWorkTimes;
    private int deletedWorktimes;

    
    public WorktimesUpdateResponse() {
        this.updatedWorkTimes = new LinkedList<Worktime>();
        this.deletedWorktimes = 0;
    }

    public List<Worktime> getUpdatedWorkTimes() {
        return updatedWorkTimes;
    }

    public void setUpdatedWorkTimes(List<Worktime> updatedWorkTimes) {
        this.updatedWorkTimes = updatedWorkTimes;
    }

    public int getDeletedWorktimes() {
        return deletedWorktimes;
    }

    public void setDeletedWorktimes(int deletedWorktimes) {
        this.deletedWorktimes = deletedWorktimes;
    }
}
