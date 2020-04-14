package com.tricast.api.responses;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeOffViewResponse {

	private static final long serialVersionUID = 1L;
	private ZonedDateTime date;
	List<OffDayResponse> offdays = new ArrayList<OffDayResponse>();
	List<PendingResponse> pendings = new ArrayList<PendingResponse>();

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}
	
	public List<OffDayResponse> getOffdays() {
		return offdays;
	}

	public void setOffdays(List<OffDayResponse> offdays) {
		this.offdays = offdays;
	}
}
