package com.tricast.api.responses;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.tricast.repositories.entities.enums.OffDayType;
import com.tricast.repositories.entities.*;

public class OffDayResponse implements Serializable{
	
	private static final long serialVersionUID = 4L;
	
	private Long userId;
	private String fullName;
	private ZonedDateTime startTime;
	private ZonedDateTime endTime;
	private long actualDayCount;
	private OffDayType type;	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String name) {
		this.fullName = name;
	}

	public ZonedDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(ZonedDateTime startTime) {
		this.startTime = startTime;
	}

	public ZonedDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(ZonedDateTime endTime) {
		this.endTime = endTime;
	}

	public long getActualDayCount() {
		return actualDayCount;
	}

	public void setActualDayCount(long actualDayCount) {
		this.actualDayCount = actualDayCount;
	}

	public OffDayType getType() {
		return type;
	}

	public void setType(OffDayType type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result + (int) (actualDayCount ^ (actualDayCount >>> 32));
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "OffDayResponse [userId=" + userId + ", fullName=" + fullName + ", startTime=" + startTime + ", endTime="
				+ endTime + ", actualDayCount=" + actualDayCount + ", type=" + type + "]";
	}	
}
