package com.tricast.api.responses;

import com.tricast.repositories.entities.enums.OffDayType;

public class OffDayResponse {
	private long id;
	private long userId;
	private String lastName;
	private String fromDay;
	private String toDay;
	private long actualDayCount;
	private OffDayType type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getuserId() {
		return userId;
	}

	public void setuserId(long userId) {
		this.userId = userId;
	}

	public String getAccountRealName() {
		return lastName;
	}

	public void setAccountRealName(String lastName) {
		this.lastName = lastName;
	}

	public String getFromDay() {
		return fromDay;
	}

	public void setFromDay(String fromDay) {
		this.fromDay = fromDay;
	}

	public String getToDay() {
		return toDay;
	}

	public void setToDay(String toDay) {
		this.toDay = toDay;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + (int) (actualDayCount ^ (actualDayCount >>> 32));
		result = prime * result + ((fromDay == null) ? 0 : fromDay.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((toDay == null) ? 0 : toDay.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "OffDayResponse [id=" + id + ", accountId=" + userId + ", accountRealName=" + lastName
				+ ", fromDay=" + fromDay + ", toDay=" + toDay + ", actualDayCount=" + actualDayCount + "]";
	}
	
	
}
