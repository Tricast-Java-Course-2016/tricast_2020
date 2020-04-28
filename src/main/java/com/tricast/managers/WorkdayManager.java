package com.tricast.managers;

import com.tricast.api.responses.WorkdayWithWorkHoursStatsGetResponse;
import org.springframework.dao.EmptyResultDataAccessException;

public interface WorkdayManager {
	WorkdayWithWorkHoursStatsGetResponse getAllWorkdayByUserIdAndMonth(int id,int roleId);
	void deleteById(long id) throws EmptyResultDataAccessException;
}
