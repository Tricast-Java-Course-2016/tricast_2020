package com.tricast.api.responses;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class WorkdayWorktimesGetResponse {

	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private ZonedDateTime date;

	private Integer userId;
	
	List<WorktimeGetResponse> worktimes = new ArrayList<WorktimeGetResponse>();

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

	public List<WorktimeGetResponse> getWorktimes() {
		return worktimes;
	}

	public void setWorktimes(List<WorktimeGetResponse> worktimes) {
		this.worktimes = worktimes;
	}
	
	
	
}
