package com.tricast.api.requests;

import java.time.ZonedDateTime;

public class SpecialDayUpdateRequest {

	@Override
	public String toString() {
		return "SpecialDayUpdateRequest [date=" + date + "]";
	}

	private static final long serialVersionUID = 1L;

	private ZonedDateTime date;

	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}

	public static long getSerialVersionSpecialDayId() {
		return serialVersionUID;
	}

}
