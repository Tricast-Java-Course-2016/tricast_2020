package com.tricast.api.requests;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.tricast.repositories.entities.enums.OffDayType;

public class OffDayRequest implements Serializable{
	
	private static final long serialVersionUID =4L;
	
	private Long userId;
	private ZonedDateTime fromDay;
	private ZonedDateTime toDay;
	private OffDayType type;

	public Long getuserId() {
		return userId;
	}

	public void setuserId(Long userID) {
		this.userId = userId;
	}

	public ZonedDateTime getFromDay() {
		return fromDay;
	}

	public void setFromDay(ZonedDateTime fromDay) {
		this.fromDay = fromDay;
	}

	public ZonedDateTime getToDay() {
		return toDay;
	}

	public void setToDay(ZonedDateTime toDay) {
		this.toDay = toDay;
	}

	public OffDayType getType() {
		return type;
	}

	public void setType(OffDayType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "OffDayRequest [ userId=" + userId + ", fromDay=" + fromDay + ", toDay=" + toDay
				+ ", type=" + type + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((fromDay == null) ? 0 : fromDay.hashCode());
		result = prime * result + ((toDay == null) ? 0 : toDay.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
}
