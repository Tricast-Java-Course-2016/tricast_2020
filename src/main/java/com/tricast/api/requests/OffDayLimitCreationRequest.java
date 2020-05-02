package com.tricast.api.requests;

import java.math.BigDecimal;

import com.tricast.repositories.entities.enums.OffDayType;

public class OffDayLimitCreationRequest {
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal maximumAmount;
	
	private OffDayType type;
	
	private Integer userId;
	
	private String year;

	public BigDecimal getMaximumAmount() {
		return maximumAmount;
	}

	public void setMaximumAmount(BigDecimal maximumAmount) {
		this.maximumAmount = maximumAmount;
	}

	public OffDayType getType() {
		return type;
	}

	public void setType(OffDayType type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
