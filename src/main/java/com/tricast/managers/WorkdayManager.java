package com.tricast.managers;

import com.tricast.api.responses.WorkdayWithWorkHoursStatsGetResponse;

public interface WorkdayManager {
	WorkdayWithWorkHoursStatsGetResponse getAllWorkdayByUserIdAndMonth(int id,int roleId);
	void deleteById(long id);
}
