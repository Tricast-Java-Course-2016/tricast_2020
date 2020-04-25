package com.tricast.api.responses;

import java.time.ZonedDateTime;

public class WorkdayGetResponse {

	private static final long serialVersionUID = 1L;
	
	private long id;

    private int workhours;
	
	private ZonedDateTime date;

	private int userId;

    public int getWorkhours() {
        return workhours;
    }

    public void setWorkhours(int workhours) {
        this.workhours = workhours;
    }
	
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
