package com.tricast.managers;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.responses.WorkdayGetResponse;
import com.tricast.api.responses.WorkdayWithWorkHoursStatsGetResponse;
import com.tricast.repositories.WorkdayRepository;
import com.tricast.repositories.WorktimeRepository;
import com.tricast.repositories.entities.Workday;
import com.tricast.repositories.entities.Worktime;
import com.tricast.repositories.models.TheCurrentMonthOfTheYear;
import com.tricast.repositories.models.WorkDaysStatManager;

@Component
public class WorkdayManagerImpl implements WorkdayManager{

	private WorkdayRepository workdayRepository;

	private WorktimeRepository worktimeRepository;

	private WorktimeManager worktimeManager;

	@Autowired
	public WorkdayManagerImpl(WorkdayRepository workdayRepository, WorktimeManager worktimeManager,WorktimeRepository worktimeRepository) {
		this.workdayRepository = workdayRepository;
		this.worktimeManager=worktimeManager;
		this.worktimeRepository = worktimeRepository;
	}

	@Override
	public void deleteById(long id) {
		worktimeManager.deleteAllWorkTimesById(id);
	}

	@Override
    public WorkdayWithWorkHoursStatsGetResponse getAllWorkdayByUserIdAndMonth(int userId) {
        TheCurrentMonthOfTheYear CurrentMonthOfTheYear = new TheCurrentMonthOfTheYear();
		List<Workday> allWorkdaysAtMonth = workdayRepository.findByUserIdAndDateBetween(userId, CurrentMonthOfTheYear.getFirstDayOfCurrentMonth(), CurrentMonthOfTheYear.getLastDayOfCurrentMonth());
		List<Long> onlyCurrentMonthWorkDayIds = getOnlyWorkdayId(allWorkdaysAtMonth);
        List<Worktime> allWorktimesAtMonthBySpecifiedUser = worktimeRepository.findAllByWorkdayIdIn(onlyCurrentMonthWorkDayIds);
        WorkDaysStatManager workDaysStatManager = new WorkDaysStatManager(allWorkdaysAtMonth, allWorktimesAtMonthBySpecifiedUser, getDateWithFirstDayOfCurrentWeek());
        return WorkdayWithWorkHoursStatsGetResponseMapper(allWorkdaysAtMonth,workDaysStatManager);
	}
    
    private WorkdayWithWorkHoursStatsGetResponse WorkdayWithWorkHoursStatsGetResponseMapper(List<Workday> allWorkdays, WorkDaysStatManager workDaysStatManager) {
        WorkdayWithWorkHoursStatsGetResponse response = new WorkdayWithWorkHoursStatsGetResponse();
        response.setWorkdaysGetResponse(workdayResponseMapper(allWorkdays,workDaysStatManager));
        response.setWorkhoursCurrentWeek(workDaysStatManager.getCurrentWeekWorkTimes());
        response.setWorkhoursPreviouseWeek(workDaysStatManager.getPreviousWeekWorkTimes());
        return response;
	}

	private ZonedDateTime getDateWithFirstDayOfCurrentWeek() {
        ZonedDateTime lt  = ZonedDateTime.now();
        lt.minusHours(lt.getHour());
        lt.minusMinutes(lt.getMinute());
        lt.minusSeconds(lt.getSecond());
        lt.minusNanos(lt.getNano());
        return lt.minusDays(lt.getDayOfWeek().getValue()-1);
	}

	private List<Long> getOnlyWorkdayId(List<Workday> workdays) {
		List<Long> onlyWorkdayIds = new LinkedList<>();
		for (Workday workday : workdays) {
			onlyWorkdayIds.add(workday.getId());
		}
		return onlyWorkdayIds;
	}

	private List<WorkdayGetResponse> workdayResponseMapper(List<Workday> allWorkdays,WorkDaysStatManager workDaysStatManager){
		List<WorkdayGetResponse> allWorkdaysGetResponse = new ArrayList<>();
		for (Workday workDay : allWorkdays) {
				WorkdayGetResponse response = new WorkdayGetResponse();
				response.setId(workDay.getId());
				response.setDate(workDay.getDate());
				response.setUserId(workDay.getUserId());
                try {
                    response.setWorkhours(workDaysStatManager.getWorkedHours().get(workDay.getId())/60);
                } catch (Exception e) {
                    response.setWorkhours(0);
                }
                
				allWorkdaysGetResponse.add(response);
		}
		return allWorkdaysGetResponse;
	}



}
