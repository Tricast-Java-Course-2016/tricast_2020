package com.tricast.managers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

	////////
	@Override
	public void deleteById(long id) {
		worktimeManager.deleteAllWorkTimesById(id);
	}

	@Override
    public WorkdayWithWorkHoursStatsGetResponse getAllWorkdayByUserIdAndMonth(int userId) {
        // AKOS COMMENT: nehezen olvasható ez a módszer
        // mindig tudnod kell hogy a lista melyik eleme mit reprezentá amit te lehet tudsz (most még)
        // de valaki aki olvassa a kódot sok metóduson kell átmennie mire ez kiderül.
        // Ilyen esetkben inkább ha tényleg szükséges, inlább hozz létre egy megfelelően elnevezett osztályt
        // az adatok tárolására. Akkor a nevekből már nyilvánvaló lesz mi is történik, egy getCurrentDay() többet mond
        // mint list.get(0)
        List<Integer> dataParams = getWithCurrentYearAndCurrentMonthAndCurrentDayofMonth();
		List<Workday> allWorkdays = workdayRepository.findByUserIdAndDateBetween(userId, CurrentFirstDayOfMonth(dataParams), CurrentLastDayOfMonth(dataParams));
		List<Integer> workhoursIntegers =sumWorkhoursInCurrentWeekAndPreviousWeek(userId);
		return WorkdayWithWorkHoursStatsGetResponseMapper(workdayResponseMapper(allWorkdays),workhoursIntegers);
	}

	private WorkdayWithWorkHoursStatsGetResponse WorkdayWithWorkHoursStatsGetResponseMapper(List<WorkdayGetResponse> workdaysGetResponse, List<Integer> workHoursInMinutes) {
		WorkdayWithWorkHoursStatsGetResponse response = new WorkdayWithWorkHoursStatsGetResponse();
		response.setWorkdaysGetResponse(workdaysGetResponse);
		return sumWorkhoursAndWorkMinutes(response,workHoursInMinutes);
	}

	private WorkdayWithWorkHoursStatsGetResponse sumWorkhoursAndWorkMinutes(WorkdayWithWorkHoursStatsGetResponse response, List<Integer> workHoursInMinutes) {
		int workHours=workHoursInMinutes.get(0)/60;
		response.setWorkhoursCurrentWeek(workHours);
		response.setWorkminutesCurrentWeek(workHoursInMinutes.get(0)-workHours*60);
		workHours=workHoursInMinutes.get(1)/60;
		response.setWorkhoursPreviouseWeek(workHours);
		response.setWorkminutesPreviouseWeek(workHoursInMinutes.get(1)-workHours*60);
		return response;
	}

	private List<Integer> sumWorkhoursInCurrentWeekAndPreviousWeek(int userId) {
		ZonedDateTime dateWithFirstDayOfCurrentWeek = getDateWithFirstDayOfCurrentWeek();
        // AKOS COMMENT: az adatbázis lekérdezés egy drága művelet és célszerű a spórolásra törekedni velük
		List<Workday> worktimeAtCurrentWeek = workdayRepository.findByUserIdAndDateBetween(userId,dateWithFirstDayOfCurrentWeek,dateWithFirstDayOfCurrentWeek.plusDays(6));
		List<Workday> worktimeAtPreviouseWeek = workdayRepository.findByUserIdAndDateBetween(userId,dateWithFirstDayOfCurrentWeek.minusWeeks(1),dateWithFirstDayOfCurrentWeek.minusWeeks(1).plusDays(6));
		int workMinutesInCurrentWeek = sumWorktimesGiveToMinutes(getOnlyWorkdayId(worktimeAtCurrentWeek));
		int workMinutesInPreviousWeek = sumWorktimesGiveToMinutes(getOnlyWorkdayId(worktimeAtPreviouseWeek));
		return Arrays.asList(workMinutesInCurrentWeek, workMinutesInPreviousWeek);
	}

	private ZonedDateTime getDateWithFirstDayOfCurrentWeek() {
        ZonedDateTime lt  = ZonedDateTime.now();
        return lt.minusDays(lt.getDayOfWeek().getValue()-1);
	}

	private List<Long> getOnlyWorkdayId(List<Workday> workdays) {
		List<Long> onlyWorkdayIds = new LinkedList<>();
		for (Workday workday : workdays) {
			onlyWorkdayIds.add(workday.getId());
		}
		return onlyWorkdayIds;
	}

	private int sumWorktimesGiveToMinutes(List<Long> workdayIds) {
		int hour=0;
		int min=0;
		List<Worktime> getWorktimesOfweek = worktimeRepository.findAllByWorkdayIdIn(workdayIds);
		for (Worktime worktime : getWorktimesOfweek) {
			hour = hour + (worktime.getEndTime().getHour() - worktime.getStartTime().getHour());
			min = min + (worktime.getEndTime().getMinute() - worktime.getStartTime().getMinute());
		}
		return min = min + hour * 60;
	}

	private List<Integer> getWithCurrentYearAndCurrentMonthAndCurrentDayofMonth() {
		List<Integer> dataParams = new LinkedList<>();
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
		List<WorkdayGetResponse> allWorkdaysGetResponse = new ArrayList<>();
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
