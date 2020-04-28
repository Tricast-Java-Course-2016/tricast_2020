package com.tricast.managers;


import java.util.Date;
import java.util.List;

import com.tricast.repositories.entities.Specialday;

public interface SpecialDayManager {
	
	List<Specialday> getSpecialDaysInTheYear(String year);
	
	Specialday createSpecialday(Specialday specialdayRequest);
	
	List<Specialday> getAllSpecialdays();
	
	void deleteSpecialday(int id);
	
	String getSpecialdayType(Date date);
}
