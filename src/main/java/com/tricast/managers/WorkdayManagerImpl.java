package com.tricast.managers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.api.requests.WorktimeCreationRequest;
import com.tricast.api.responses.WorkdayCreationResponse;
import com.tricast.api.responses.WorkdayGetResponse;
import com.tricast.api.responses.WorktimeCreationResponse;
import com.tricast.api.responses.WorktimeGetResponse;
import com.tricast.repositories.WorkdayRepository;
import com.tricast.repositories.WorktimeRepository;
import com.tricast.repositories.entities.Workday;
import com.tricast.repositories.entities.Worktime;

@Component
public class WorkdayManagerImpl implements WorkdayManager{
	
	private WorkdayRepository workdayRepository;
	
	private WorktimeManager worktimeManager;
	
	@Autowired
	public WorkdayManagerImpl(WorkdayRepository workdayRepository, WorktimeManager worktimeManager) {
		this.workdayRepository = workdayRepository;
		this.worktimeManager = worktimeManager;
	}	

	////////
	@Override
	public void deleteById(long id) {
		worktimeManager.deleteAllWorkTimesById(id);
		workdayRepository.deleteById(id);
	}

	////////
	@Override
	public List<WorkdayGetResponse> getAllWorkdayByUserIdAndMonth(int userId) {
		List<Integer> dataParams = getWithCurrentYearAndCurrentMonthAndCurrentDayofMonth();
		List<Workday> allWorkdays = (List<Workday>)workdayRepository.findByUserIdAndDateBetween(userId, CurrentFirstDayOfMonth(dataParams), CurrentLastDayOfMonth(dataParams));
		return workdayResponseMapper(allWorkdays);
	}
	
	private List<Integer> getWithCurrentYearAndCurrentMonthAndCurrentDayofMonth() { 
		List<Integer> dataParams = new LinkedList<Integer>();
		dataParams.add(ZonedDateTime.now().getYear());
		dataParams.add(ZonedDateTime.now().getMonthValue());
		dataParams.add(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
		return dataParams;
	}
	
	private ZonedDateTime CurrentFirstDayOfMonth(List<Integer> dateParam) {
		return  ZonedDateTime.of(dateParam.get(0), dateParam.get(1), 1, 0, 0, 0, 000, ZoneId.systemDefault());
	}
	
	private ZonedDateTime CurrentLastDayOfMonth(List<Integer> dateParam) {
		return  ZonedDateTime.of(dateParam.get(0), dateParam.get(1), dateParam.get(2), 0, 0, 0, 000, ZoneId.systemDefault());
	}
	
	private List<WorkdayGetResponse> workdayResponseMapper(List<Workday> allWorkdays){
		List<WorkdayGetResponse> allWorkdaysGetResponse = new ArrayList<WorkdayGetResponse>();
		for (Workday workDay : allWorkdays) {
				WorkdayGetResponse response = new WorkdayGetResponse();
				response.setId(workDay.getId());
				response.setDate(workDay.getDate());
				response.setUserId(workDay.getUserId());
				allWorkdaysGetResponse.add(response);
		}
		return allWorkdaysGetResponse;
	}


	
}
