package com.tricast.api.requests;

import java.util.LinkedList;
import java.util.List;


public class WorkTimeUpdateListRequest {

	private List<WorkTimeUpdateRequest> worktimesList;

    private int userId;
    
	public List<WorkTimeUpdateRequest> getDatasList() {
		return worktimesList;
	}

	public void setDatasList(List<WorkTimeUpdateRequest> worktimesList) {
		this.worktimesList = worktimesList;
	}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
