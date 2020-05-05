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
import com.tricast.managers.custom_classes.CurrentMonthOfYear;
import com.tricast.managers.custom_classes.WorkDaysStat;

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
        //workdayRepository.deleteById(id);
	}

	@Override
    public WorkdayWithWorkHoursStatsGetResponse getAllWorkdayByUserIdAndMonth(int userId,int roleId) {
        CurrentMonthOfYear currentMonthOfYear = new CurrentMonthOfYear();
        //MARK: Ha hónap forduló van az előző hetet nem számolja javítani kell
		List<Workday> allWorkdaysAtMonth = workdayRepository.findByUserIdAndDateBetween(userId, currentMonthOfYear.getFirstDayOfCurrentMonth(), currentMonthOfYear.getLastDayOfCurrentMonth());
		List<Long> onlyCurrentMonthWorkDayIds = getOnlyWorkdayId(allWorkdaysAtMonth);
        // AKOS2: ha a Workday-re már rájoin-oljtok a WorkTime-okat akkor nem kell külön betölteni
		List<Worktime> allWorktimesAtMonthBySpecifiedUser = worktimeRepository.findAllByWorkdayIdIn(onlyCurrentMonthWorkDayIds);
        WorkDaysStat workDaysStatManager = new WorkDaysStat(allWorkdaysAtMonth, allWorktimesAtMonthBySpecifiedUser, getDateWithFirstDayOfCurrentWeek());
        if(Role.getById(roleId)== Role.ADMIN){
            Map<Long,String> usersList = usersListMapper((List<User>) userRepository.findAll());
            return WorkdayWithWorkHoursStatsGetResponseMapper(allWorkdaysAtMonth,workDaysStatManager,usersList);
        }else{
            return workdayWithWorkHoursStatsGetResponseMapper(allWorkdaysAtMonth,workDaysStatManager);
        }
	}

    private Map<Long,String> usersListMapper(List<User> usersList){
        Map<Long,String> usersListContainerUserIdAndUsername = new HashMap<>();
        usersList.forEach((user) -> {
            usersListContainerUserIdAndUsername.put(user.getId(), user.getUserName());
        });
        return usersListContainerUserIdAndUsername;
    }

    private WorkdayWithWorkHoursStatsGetResponse workdayWithWorkHoursStatsGetResponseMapper(List<Workday> allWorkdays, WorkDaysStat workDaysStatManager) {
        WorkdayWithWorkHoursStatsGetResponse response = new WorkdayWithWorkHoursStatsGetResponse();
        response.setWorkdaysGetResponse(workdayResponseMapper(allWorkdays,workDaysStatManager));
        response.setWorkhoursCurrentWeek(workDaysStatManager.getCurrentWeekWorkTimes());
        response.setWorkhoursPreviouseWeek(workDaysStatManager.getPreviousWeekWorkTimes());
        response.setWorkminutesCurrentWeek(workDaysStatManager.getCurrentWeekWorkMinutes());
        response.setWorkminutesPreviouseWeek(workDaysStatManager.getPreviousWeekWorkTimesMinutes());
        response.setUserList(null);
        return response;
	}
    
    private WorkdayWithWorkHoursStatsGetResponse WorkdayWithWorkHoursStatsGetResponseMapper(List<Workday> allWorkdays, WorkDaysStat workDaysStatManager, Map<Long,String> usersList) {
        WorkdayWithWorkHoursStatsGetResponse response = workdayWithWorkHoursStatsGetResponseMapper(allWorkdays,workDaysStatManager);
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

	private List<WorkdayGetResponse> workdayResponseMapper(List<Workday> allWorkdays,WorkDaysStat workDaysStatManager){
		List<WorkdayGetResponse> allWorkdaysGetResponse = new ArrayList<>();
		for (Workday workDay : allWorkdays) {
				WorkdayGetResponse response = new WorkdayGetResponse();
				response.setId(workDay.getId());
				response.setDate(workDay.getDate());
				response.setUserId(workDay.getUserId());
                
                if(workDaysStatManager.getWorkedHours().containsKey(workDay.getId())){
                    response.setWorkhours(workDaysStatManager.getWorkedHours().get(workDay.getId())/60);
                    response.setWorkMinutes(workDaysStatManager.calculateMinutes(workDaysStatManager.getWorkedHours().get(workDay.getId())));
                }
                else{
                    response.setWorkhours(0);
                    response.setWorkMinutes(0);
                }
				allWorkdaysGetResponse.add(response);
		}
		return allWorkdaysGetResponse;
	}



}
