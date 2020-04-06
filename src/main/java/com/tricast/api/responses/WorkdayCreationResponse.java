package com.tricast.api.responses;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tricast.repositories.entities.Worktime;

public class WorkdayCreationResponse {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private ZonedDateTime date;

	private Integer userId;
	
	private List<WorktimeCreationResponse> worktimesCreatioenResponse = new ArrayList<WorktimeCreationResponse>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public static long getSerialversionworkdayid() {
		return serialVersionUID;
	}

	public List<WorktimeCreationResponse> getWorktimesCreatioenResponse() {
		return worktimesCreatioenResponse;
	}

	public void setWorktimesCreatioenResponse(List<WorktimeCreationResponse> worktimesCreatioenResponse) {
		this.worktimesCreatioenResponse = worktimesCreatioenResponse;
	}

	
	
	
}
