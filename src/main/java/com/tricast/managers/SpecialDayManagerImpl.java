package com.tricast.managers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.requests.SpecialDayUpdateRequest;
import com.tricast.api.responses.SpecialDayResponse;
import com.tricast.repositories.SpecialdayRepository;
import com.tricast.repositories.entities.Specialday;

@Component
public class SpecialDayManagerImpl implements SpecialDayManager {

	private SpecialdayRepository specialdayRepository;
	// ORSI
	// Ez ne legyen a manager osztályváltozója, a type a SpecialDaysResponse
	// objektumhoz tartozik
	// private String type;

	@Autowired
	public SpecialDayManagerImpl(SpecialdayRepository specialdayRepository) {
		this.specialdayRepository = specialdayRepository;
	}

	public SpecialDayManagerImpl() {

	}

	@Override
	public Optional<Specialday> getById(long id) {
		return specialdayRepository.findById(id);
	}

	@Override
	public SpecialDayResponse createSpecialday(SpecialDayUpdateRequest specialDayUpdateRequest) {
		Specialday specialday = mapSpecialdayCreationRequestToSpecialday(specialDayUpdateRequest);
		Specialday newSpecialday = specialdayRepository.save(specialday);
		return mapSpacialdayToSpecialDayResponse(newSpecialday);
	}

	@Override
	public void deleteSpecialday(int id) {
		specialdayRepository.deleteById((long) id);
	}

	@Override
	public List<SpecialDayResponse> getSpecialDaysInTheYear(String year) {
		if (year != null) {
			List<Specialday> specialdays = specialdayRepository.getAllByDateBetween(
					getTheYearFirstDay(Integer.parseInt(year)), getTheYearFinalDay(Integer.parseInt(year)));
			List<SpecialDayResponse> specialdayList = new ArrayList<>();

			for (Specialday specialday : specialdays) {
				specialdayList.add(mapSpacialdayToSpecialDayResponse(specialday));
			}
			return specialdayList;

		} else {
			List<Specialday> specialdays = specialdayRepository.getAllByDateBetween(
					getTheYearFirstDay(getTheCurrentYear()), getTheYearFinalDay(getTheCurrentYear()));
			List<SpecialDayResponse> specialdayList = new ArrayList<>();

			for (Specialday specialday : specialdays) {
				specialdayList.add(mapSpacialdayToSpecialDayResponse(specialday));
			}
			return specialdayList;
		}

	}

	@Override
	public List<SpecialDayResponse> getAllSpecialDay() {

		List<Specialday> specialdays = specialdayRepository.findAll();
		List<SpecialDayResponse> specialdayList = new ArrayList<>();

		for (Specialday specialday : specialdays) {
			specialdayList.add(mapSpacialdayToSpecialDayResponse(specialday));
		}
		return specialdayList;
	}

	private SpecialDayResponse mapSpacialdayToSpecialDayResponse(Specialday specialday) {

		SpecialDayResponse updatedSpecialday = new SpecialDayResponse();

		updatedSpecialday.setId(specialday.getId());
		updatedSpecialday.setDate(specialday.getDate());
		updatedSpecialday.setType(specialday.getDate());

		return updatedSpecialday;
	}
	// ORSI
	// Itt kellene a Specialday listából egy SpecialDayResponse listát csinálni.
	// Hasonlóan mint ahogy a
	// UserManagerImpl.mapUserToUserResponse() metódusban láthatod.
	// A response építése közben kellene meghívnod a getSpecialdayType() metódust
	// is, innen a manager osztályból.

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

	private Specialday mapSpecialdayCreationRequestToSpecialday(SpecialDayUpdateRequest specialDayUpdateRequest) {
		Specialday newSpecialday = new Specialday();
		newSpecialday.setDate(specialDayUpdateRequest.getDate());

		return newSpecialday;
	}

	@Override
	public String getSpecialdayType(ZonedDateTime date) {
		Date inputDate = Date.from(date.toInstant());
		String day = inputDate.toString();
		String type;
		// ORSI
		// Legyen itt egy lokális változó a type-nak és adjuk vissza azt
		if (day.substring(0, 3) == "Sat") {
			type = "WORKDAY";
		} else {
			type = "HOLIDAY";
		}
		return type;
	}
}
