package com.tricast.managers;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.tricast.api.requests.SpecialDayUpdateRequest;
import com.tricast.api.responses.SpecialDayResponse;
import com.tricast.repositories.entities.Specialday;

public interface SpecialDayManager {

	List<SpecialDayResponse> getSpecialDaysInTheYear(String year);

	public SpecialDayResponse createSpecialday(SpecialDayUpdateRequest specialDayUpdateRequest);

	public List<SpecialDayResponse> getAllSpecialDay();

	public Optional<Specialday> getById(long id);

	List<Specialday> getAllSpecialdays();

	void deleteSpecialday(int id);

	String getSpecialdayType(ZonedDateTime date);
}
