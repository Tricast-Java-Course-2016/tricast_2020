package com.tricast.managers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.repositories.SpecialdayRepository;
import com.tricast.repositories.entities.Specialday;

@Component
public class SpecialDayManagerImpl implements SpecialDayManager {

	private SpecialdayRepository specialdayRepository;
    // ORSI
    // Ez ne legyen a manager osztályváltozója, a type a SpecialDaysResponse objektumhoz tartozik
	private String type;

	@Autowired
	public SpecialDayManagerImpl(SpecialdayRepository specialdayRepository) {
		this.specialdayRepository = specialdayRepository;
	}
	public SpecialDayManagerImpl() {

	}

	@Override
	public Specialday createSpecialday(Specialday specialdayRequest) {
		return specialdayRepository.save(specialdayRequest);
	}

	@Override
	public void deleteSpecialday(int id) {
		specialdayRepository.deleteById((long) id);
	}

	@Override
	public List<Specialday> getSpecialDaysInTheYear(String year) {
		if (year != null) {
			return specialdayRepository.getAllByDateBetween(getTheYearFirstDay(Integer.parseInt(year)),
					getTheYearFinalDay(Integer.parseInt(year)));
		} else {
			return specialdayRepository.getAllByDateBetween(getTheYearFirstDay(getTheCurrentYear()),
					getTheYearFinalDay(getTheCurrentYear()));
		}

        // ORSI
        // Itt kellene a Specialday listából egy SpecialDayResponse listát csinálni. Hasonlóan mint ahogy a
        // UserManagerImpl.mapUserToUserResponse() metódusban láthatod.
        // A response építése közben kellene meghívnod a getSpecialdayType() metódust is, innen a manager osztályból.
	}

	private int getTheCurrentYear() {
		return ZonedDateTime.now().getYear();
	}

	private ZonedDateTime getTheYearFirstDay(int year) {
		return ZonedDateTime.of(year, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());
	}

	private ZonedDateTime getTheYearFinalDay(int year) {
		return ZonedDateTime.of(year, 12, getWithDayOfMonth(), 23, 59, 59, 999, ZoneId.systemDefault());
	}

	private int getWithDayOfMonth() {
		return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	@Override
	public List<Specialday> getAllSpecialdays() {
		return (List<Specialday>) specialdayRepository.findAll();
	}

	@Override
	public String getSpecialdayType(Date date) {
		String day = date.toString();
        // ORSI
        // Legyen itt egy lokális változó a type-nak és adjuk vissza azt
		if (day.substring(0, 3) == "Sat") {
            this.type = "WORKDAY";
		} else {
            this.type = "HOLIDAY";
		}
        return this.type;
	}
}
