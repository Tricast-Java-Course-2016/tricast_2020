package com.tricast.api.requests;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.tricast.repositories.entities.enums.OffDayType;

public class OffDayRequest implements Serializable{

	private static final long serialVersionUID = 4L;

	private Long userId;
	private String startTime;
	private String endTime;
	private OffDayType type;

	public Long getuserId() {
		return userId;
	}

    public void setuserId(Long userId) {
		this.userId = userId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public OffDayType getType() {
		return type;
	}

	public void setType(OffDayType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + (int)((userId == null) ? 0 : userId.hashCode()); // long <-> LONG, cannot invoke
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return "OffDayRequest [ userId=" + userId + ", fromDay=" + startTime + ", toDay=" + endTime
				+ ", type=" + type + "]";
	}
}
