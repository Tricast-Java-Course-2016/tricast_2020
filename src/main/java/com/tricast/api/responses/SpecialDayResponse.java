package com.tricast.api.responses;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tricast.managers.SpecialDayManagerImpl;

public class SpecialDayResponse {

	private static final long serialVersionUID = 1870713627974030003L;

	private ZonedDateTime date;

	private String type;

    // ORSI
    // Manager és ManagerImpl osztályokat így nem szabad belerakni a response objektumba. A response osztályba csak
    // azokat az adatokat kell belerakni, amikre szükség van a kliens oldalon
	private SpecialDayManagerImpl sdmi = new SpecialDayManagerImpl();

    // ORSI
    // Szerintem erre a listára nincs szükséged a response objektumon belül, összeségében csak egy
    // List<SpecialDayResponse> listát kellene visszaadni
	List<SpecialDayResponse> specialDays = new ArrayList<>();


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

	public String getType() {
		Date inputDate = Date.from(date.toInstant());
		type = sdmi.getSpecialdayType(inputDate);
		return type;
	}
}
