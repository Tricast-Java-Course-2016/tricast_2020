/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tricast.api.responses;

import java.util.LinkedList;
import java.util.List;

public class WorktimesUpdateResponse {

    private List<WorktimeGetResponse> updatedWorkTimes;
    private int deletedWorktimes;

    
    public WorktimesUpdateResponse() {
        this.updatedWorkTimes = new LinkedList<WorktimeGetResponse>();
        this.deletedWorktimes = 0;
    }

    public List<WorktimeGetResponse> getUpdatedWorkTimes() {
        return updatedWorkTimes;
    }

    public void setUpdatedWorkTimes(List<WorktimeGetResponse> updatedWorkTimes) {
        this.updatedWorkTimes = updatedWorkTimes;
    }

    public int getDeletedWorktimes() {
        return deletedWorktimes;
    }

    public void setDeletedWorktimes(int deletedWorktimes) {
        this.deletedWorktimes = deletedWorktimes;
    }
}
