package com.tricast.api.requests;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tricast.repositories.entities.Worktime;

public class WorkdayCreationRequest {

	private static final long serialVersionWorkdayID = 1L;
	
	//private long id;
	
	private ZonedDateTime date;

	private Integer userId;
	
	
	//Worktime
	
	List<WorktimeCreationRequest> worktimesCreationRequest = new ArrayList<WorktimeCreationRequest>();
	
	
	
	
	
	
	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}

	public static long getSerialversionworkdayid() {
		return serialVersionWorkdayID;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<WorktimeCreationRequest> getWorktimesCreationRequest() {
		return worktimesCreationRequest;
	}

	public void setWrktimesCreationRequest(List<WorktimeCreationRequest> worktimesCreationRequest) {
		this.worktimesCreationRequest = worktimesCreationRequest;
	}

	

	
	
	
}
