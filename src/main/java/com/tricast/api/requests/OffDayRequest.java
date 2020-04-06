package com.tricast.api.requests;

import com.tricast.repositories.entities.enums.OffDayType;

public class OffDayRequest {
	private Long id;
	private Long userId;
	private String fromDay;
	private String toDay;
	private OffDayType type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getusertId() {
		return userId;
	}

	public void setAccountId(Long accountId) {
		this.userId = userId;
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

	public OffDayType getType() {
		return type;
	}

	public void setType(OffDayType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "OffDayRequest [id=" + id + ", userId=" + userId + ", fromDay=" + fromDay + ", toDay=" + toDay
				+ ", type=" + type + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((fromDay == null) ? 0 : fromDay.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((toDay == null) ? 0 : toDay.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
}
