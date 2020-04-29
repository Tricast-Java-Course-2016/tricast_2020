package com.tricast.managers;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.tricast.api.responses.WorkdayGetResponse;
import com.tricast.api.responses.WorkdayWithWorkHoursStatsGetResponse;
import com.tricast.repositories.UserRepository;
import com.tricast.repositories.WorkdayRepository;
import com.tricast.repositories.WorktimeRepository;
import com.tricast.repositories.entities.User;
import com.tricast.repositories.entities.Workday;
import com.tricast.repositories.entities.Worktime;
import com.tricast.repositories.entities.enums.Role;
import com.tricast.repositories.models.TheCurrentMonthOfTheYear;
import com.tricast.repositories.models.WorkDaysStatManager;

@Component
public class WorkdayManagerImpl implements WorkdayManager{

	private WorkdayRepository workdayRepository;

	private WorktimeRepository worktimeRepository;

	private WorktimeManager worktimeManager;

    private UserRepository userRepository;

	@Autowired
	public WorkdayManagerImpl(WorkdayRepository workdayRepository, WorktimeManager worktimeManager,WorktimeRepository worktimeRepository,UserRepository userRepository) {
		this.workdayRepository = workdayRepository;
		this.worktimeManager=worktimeManager;
		this.worktimeRepository = worktimeRepository;
        this.userRepository = userRepository;
	}

	@Override
	public void deleteById(long id) throws EmptyResultDataAccessException{
		worktimeManager.deleteAllWorkTimesById(id);
	}

	@Override
    public WorkdayWithWorkHoursStatsGetResponse getAllWorkdayByUserIdAndMonth(int userId,int roleId) {
        // AKOS2: ezt elég lenne CurrentMonthOfYear -nek nevezni
        // a töltelék szavakat, jelen eseben a 'The' célszerű kerülni
        TheCurrentMonthOfTheYear CurrentMonthOfTheYear = new TheCurrentMonthOfTheYear();
		List<Workday> allWorkdaysAtMonth = workdayRepository.findByUserIdAndDateBetween(userId, CurrentMonthOfTheYear.getFirstDayOfCurrentMonth(), CurrentMonthOfTheYear.getLastDayOfCurrentMonth());
		List<Long> onlyCurrentMonthWorkDayIds = getOnlyWorkdayId(allWorkdaysAtMonth);
        // AKOS2: ha a Workday-re már rájoin-oljtok a WorkTime-okat akkor nem kell külön betölteni
		List<Worktime> allWorktimesAtMonthBySpecifiedUser = worktimeRepository.findAllByWorkdayIdIn(onlyCurrentMonthWorkDayIds);
        WorkDaysStatManager workDaysStatManager = new WorkDaysStatManager(allWorkdaysAtMonth, allWorktimesAtMonthBySpecifiedUser, getDateWithFirstDayOfCurrentWeek());
        if(Role.getById(roleId)== Role.ADMIN){
            Map<Long,String> usersList = UsersListMapper((List<User>) userRepository.findAll());
            return WorkdayWithWorkHoursStatsGetResponseMapper(allWorkdaysAtMonth,workDaysStatManager,usersList);
        }else{
            return WorkdayWithWorkHoursStatsGetResponseMapper(allWorkdaysAtMonth,workDaysStatManager);
        }
	}

    // AKOS2: nagy betűvel kezdődő metódus nevek
    private Map<Long,String> UsersListMapper(List<User> usersList){
        Map<Long,String> usersListContainerUserIdAndUsername = new HashMap<>();
        usersList.forEach((user) -> {
            usersListContainerUserIdAndUsername.put(user.getId(), user.getUserName());
        });
        return usersListContainerUserIdAndUsername;
    }

    private WorkdayWithWorkHoursStatsGetResponse WorkdayWithWorkHoursStatsGetResponseMapper(List<Workday> allWorkdays, WorkDaysStatManager workDaysStatManager) {
        WorkdayWithWorkHoursStatsGetResponse response = new WorkdayWithWorkHoursStatsGetResponse();
        response.setWorkdaysGetResponse(workdayResponseMapper(allWorkdays,workDaysStatManager));
        response.setWorkhoursCurrentWeek(workDaysStatManager.getCurrentWeekWorkTimes());
        response.setWorkhoursPreviouseWeek(workDaysStatManager.getPreviousWeekWorkTimes());
        response.setUserList(null);
        return response;
	}

    // AKOS2: ezt a 2 metódust:
    // - WorkdayWithWorkHoursStatsGetResponseMapper
    // amiknek még a neve is ugyan az kicsit le lehetne rövidíteni
    // elég sok a kód duplikáció bennük amit kerülni kellene
    // a "szűkebb" metódust meghagynám így (ahol nincs a userList kitöltve)
    // a másikból pedig ezt a metódust hívnám meg
    // plusz az az gy sor ami beállítja a userList-et
    private WorkdayWithWorkHoursStatsGetResponse WorkdayWithWorkHoursStatsGetResponseMapper(List<Workday> allWorkdays, WorkDaysStatManager workDaysStatManager, Map<Long,String> usersList) {
        WorkdayWithWorkHoursStatsGetResponse response = new WorkdayWithWorkHoursStatsGetResponse();
        response.setWorkdaysGetResponse(workdayResponseMapper(allWorkdays,workDaysStatManager));
        response.setWorkhoursCurrentWeek(workDaysStatManager.getCurrentWeekWorkTimes());
        response.setWorkhoursPreviouseWeek(workDaysStatManager.getPreviousWeekWorkTimes());
        response.setUserList(usersList);
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
                // AKOS2: gondolom ez azért nem az elvárt viselkedés
                // szóval itt vélszerő lenne error levelen kiloggolni az exception-t
                    response.setWorkhours(0);
                }

				allWorkdaysGetResponse.add(response);
		}
		return allWorkdaysGetResponse;
	}



}
