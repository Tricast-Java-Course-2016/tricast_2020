package com.tricast.managers;

import com.tricast.api.requests.WorkdayByMounthRequest;
import com.tricast.api.responses.WorkdayWithWorkHoursStatsGetResponse;
import org.springframework.dao.EmptyResultDataAccessException;

public interface WorkdayManager {
	WorkdayWithWorkHoursStatsGetResponse getAllWorkdayByUserId(int id,int roleId);
    WorkdayWithWorkHoursStatsGetResponse getAllWorkdayByUserIdAndMonth(int id,int roleId,WorkdayByMounthRequest workdayByMounthRequest);
	void deleteById(long id) throws EmptyResultDataAccessException;
}
