package com.tricast.api.responses;

import java.time.ZonedDateTime;
import java.util.Date;

import com.tricast.repositories.entities.enums.SpecialDayType;

public class SpecialDayResponse {

	private static final long serialVersionUID = 1870713627974030003L;

	private long id;

	private ZonedDateTime date;

	private String type;

	// ORSI
	// Manager és ManagerImpl osztályokat így nem szabad belerakni a response
	// objektumba. A response osztályba csak
	// azokat az adatokat kell belerakni, amikre szükség van a kliens oldalon
	// private SpecialDayManagerImpl sdmi = new SpecialDayManagerImpl();

	// ORSI
	// Szerintem erre a listára nincs szükséged a response objektumon belül,
	// összeségében csak egy
	// List<SpecialDayResponse> listát kellene visszaadni

	public static long getSerialVersionSpecialDayId() {
		return serialVersionUID;
	}

	public ZonedDateTime getDate() {
		return date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}

	public String setType(ZonedDateTime date) {
		// Date inputDate = Date.from(date.toInstant());
		this.type = getSpecialdayType(date);
		return this.type;
	}
	
	public String getType() {
		return type;
	}

	private String getSpecialdayType(ZonedDateTime date) {
		Date inputDate = Date.from(date.toInstant());
		String day = inputDate.toString();
		String type;

		if (day.substring(0, 3) == "Sat") {
			type = SpecialDayType.WORKDAY.name();
		} else {
			type = SpecialDayType.HOLIDAY.name();
		}
		return type;
	}
}
