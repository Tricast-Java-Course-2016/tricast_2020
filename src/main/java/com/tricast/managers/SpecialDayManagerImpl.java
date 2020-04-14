package com.tricast.managers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.repositories.SpecialdayRepository;
import com.tricast.repositories.entities.Specialday;

@Component
public class SpecialDayManagerImpl implements SpecialDayManager{

	private SpecialdayRepository specialdayRepository;
	
	@Autowired
	public SpecialDayManagerImpl(SpecialdayRepository specialdayRepository) {
		this.specialdayRepository = specialdayRepository;
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
		if(year != null) {
			return specialdayRepository.getAllByDateBetween(getTheYearFirstDay(Integer.parseInt(year)), getTheYearFinalDay(Integer.parseInt(year)));
		}
		else {
			return specialdayRepository.getAllByDateBetween(getTheYearFirstDay(getTheCurrentYear()), getTheYearFinalDay(getTheCurrentYear()));
		}

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
}
