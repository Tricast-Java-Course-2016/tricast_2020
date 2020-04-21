package com.tricast.api.requests;

import java.util.LinkedList;
import java.util.List;


public class WorkTimeUpdateListRequest {

	private List<WorkTimeUpdateRequest> worktimesList = new LinkedList<WorkTimeUpdateRequest>();

    
    
	public List<WorkTimeUpdateRequest> getDatasList() {
		return worktimesList;
	}

	public void setDatasList(List<WorkTimeUpdateRequest> worktimesList) {
		this.worktimesList = worktimesList;
	}
}
