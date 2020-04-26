package com.tricast.managers;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashMap;
import java.util.Map;

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
	public void deleteById(long id) {
		worktimeManager.deleteAllWorkTimesById(id);
	}

	@Override
    public WorkdayWithWorkHoursStatsGetResponse getAllWorkdayByUserIdAndMonth(int userId,int roleId) {
        TheCurrentMonthOfTheYear CurrentMonthOfTheYear = new TheCurrentMonthOfTheYear();
		List<Workday> allWorkdaysAtMonth = workdayRepository.findByUserIdAndDateBetween(userId, CurrentMonthOfTheYear.getFirstDayOfCurrentMonth(), CurrentMonthOfTheYear.getLastDayOfCurrentMonth());
		List<Long> onlyCurrentMonthWorkDayIds = getOnlyWorkdayId(allWorkdaysAtMonth);
        List<Worktime> allWorktimesAtMonthBySpecifiedUser = worktimeRepository.findAllByWorkdayIdIn(onlyCurrentMonthWorkDayIds);
        WorkDaysStatManager workDaysStatManager = new WorkDaysStatManager(allWorkdaysAtMonth, allWorktimesAtMonthBySpecifiedUser, getDateWithFirstDayOfCurrentWeek());
        if(Role.getById(roleId)== Role.ADMIN){
            Map<Long,String> usersList = UsersListMapper((List<User>) userRepository.findAll());
            return WorkdayWithWorkHoursStatsGetResponseMapper(allWorkdaysAtMonth,workDaysStatManager,usersList);
        }else{
            return WorkdayWithWorkHoursStatsGetResponseMapper(allWorkdaysAtMonth,workDaysStatManager);
        }
	}
    
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
                    response.setWorkhours(0);
                }
                
				allWorkdaysGetResponse.add(response);
		}
		return allWorkdaysGetResponse;
	}



}
