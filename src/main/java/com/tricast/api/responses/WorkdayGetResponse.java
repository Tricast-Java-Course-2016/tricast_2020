package com.tricast.api.responses;

import java.time.ZonedDateTime;

public class WorkdayGetResponse {

	private static final long serialVersionUID = 1L;
	
	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private ZonedDateTime date;

	private int userId;

	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
