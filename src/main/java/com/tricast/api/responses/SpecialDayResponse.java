package com.tricast.api.responses;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpecialDayResponse {

	private static final long serialVersionUID = 1870713627974030003L;
	
	private ZonedDateTime date;
	
	List<SpecialDayResponse> specialDays = new ArrayList<SpecialDayResponse>();
	
	public List<SpecialDayResponse> getSpecialDays() {
		return specialDays;
	}

	public void setSpecialDays(List<SpecialDayResponse> specialDays) {
		this.specialDays = specialDays;
	}
	
	public static long getSerialVersionSpecialDayId() {
		return serialVersionUID;
	}
	
	public ZonedDateTime getDate() {
		return date;
	}
	
	public void setDate(ZonedDateTime date) {
		this.date = date;
	}
}
