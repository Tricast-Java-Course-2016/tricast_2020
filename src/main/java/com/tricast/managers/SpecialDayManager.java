package com.tricast.managers;

import java.util.List;

import com.tricast.repositories.entities.Specialday;

public interface SpecialDayManager {
	
	List<Specialday> getSpecialDaysInTheYear(String year);
	
	Specialday createSpecialday(Specialday specialdayRequest);
	
	void deleteSpecialday(int id);
}
