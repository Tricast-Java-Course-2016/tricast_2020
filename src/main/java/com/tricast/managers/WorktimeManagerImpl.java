package com.tricast.managers;

import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.requests.WorkTimeUpdateListRequest;
import com.tricast.api.requests.WorkTimeUpdateRequest;
import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.api.requests.WorktimeCreationRequest;
import com.tricast.api.responses.WorkTimeStatByIdResponse;
import com.tricast.api.responses.WorkdayCreationResponse;
import com.tricast.api.responses.WorktimeCreationResponse;
import com.tricast.api.responses.WorktimeGetResponse;
import com.tricast.api.responses.WorktimesUpdateResponse;
import com.tricast.repositories.WorkdayRepository;
import com.tricast.repositories.WorktimeRepository;
import com.tricast.repositories.entities.Workday;
import com.tricast.repositories.entities.Worktime;
import com.tricast.managers.custom_classes.DateManager;

@Component
public class WorktimeManagerImpl implements WorktimeManager{

	private WorktimeRepository worktimeRepository;
	private WorkdayRepository workdayRepository;
    private static final int ALL_WORKHOURS_OF_YEAR = 1920;
    private static final int NUMBER_OF_WEEKS_IN_YEAR = 53;

	@Autowired
	public WorktimeManagerImpl(WorktimeRepository worktimeRepository, WorkdayRepository workdayRepository) {
		this.worktimeRepository = worktimeRepository;
		this.workdayRepository = workdayRepository;
	}

	@Override
	public List<WorktimeGetResponse> getAllWorktimeByWorktimeId(int loggedInUser,long workdayId) throws Exception{
        try {
            userCheck(loggedInUser, workdayId);
            return worktimeMapper(worktimeRepository.findAllByWorkdayId(workdayId));
        } catch (IllegalAccessException e) {
            throw e;
        }
	}

    private void userCheck(long loggedInUser, long workdayId)throws Exception{
        Optional<Workday> theRequestedWorkday = workdayRepository.findById(workdayId);
        if(theRequestedWorkday.get().getUserId().intValue() !=loggedInUser) {
            throw new IllegalAccessException("Permission denied");
        }
    }

    @Override
    public List<WorktimeGetResponse> getAllWorktimeByWorktimeId(long workdayId) throws Exception {
        return worktimeMapper(worktimeRepository.findAllByWorkdayId(workdayId));
    }
    
    private List<WorktimeGetResponse> worktimeMapper(List<Worktime> worktimes){
        List<WorktimeGetResponse> listOfResponseWorktimes = new ArrayList<>();
        worktimes.forEach( worktime-> {
            WorktimeGetResponse wortimeResponse = new WorktimeGetResponse();
            wortimeResponse.setId(worktime.getId());
            wortimeResponse.setComment(worktime.getComment());
            wortimeResponse.setEndTime(worktime.getEndTime());
            wortimeResponse.setStartTime(worktime.getStartTime());
            wortimeResponse.setType(worktime.getType());
            wortimeResponse.setWorkdayId(worktime.getWorkdayId());
            wortimeResponse.setModifiedBy(worktime.getModifiedBy());
            listOfResponseWorktimes.add(wortimeResponse);
        });
        return listOfResponseWorktimes;
    }

	@Override
	public WorkdayCreationResponse createWorkdayWithWorktimeFromRequest(WorkdayCreationRequest workdayCreationRequest) {

		Workday newWorkday = requestWorkdayMapper(workdayCreationRequest);
		Workday createdWorkday = workdayRepository.save(newWorkday);
        List<Worktime> newWorktimes = mapWorktimeCreationRequestToWorktime(workdayCreationRequest,createdWorkday);
		List<Worktime> createdWorktimes =  (List<Worktime>)worktimeRepository.saveAll(newWorktimes);
		WorkdayCreationResponse responseNewWorkday = responseNewWorkdayMapper(createdWorkday);
	    List <WorktimeCreationResponse> worktimeCreationResponse = responseNewWorktimesMapper(createdWorktimes);
		return responseNewWorkdayWithNewWorktimes(responseNewWorkday,worktimeCreationResponse);

	}

	private Workday requestWorkdayMapper(WorkdayCreationRequest workdayCreationRequest) {
		Workday newWorkDay = new Workday();
		newWorkDay.setDate(workdayCreationRequest.getDate());
		newWorkDay.setUserId(workdayCreationRequest.getUserId());
		return newWorkDay;
	}

	private WorkdayCreationResponse responseNewWorkdayMapper(Workday createdWorkday) {
		WorkdayCreationResponse workdayCreationResponse = new WorkdayCreationResponse();
		workdayCreationResponse.setDate(createdWorkday.getDate());
		workdayCreationResponse.setId(createdWorkday.getId());
		workdayCreationResponse.setUserId(createdWorkday.getUserId());
		return workdayCreationResponse;
	}

	private List<WorktimeCreationResponse> responseNewWorktimesMapper(List <Worktime> createdWorktimes) {

		List<WorktimeCreationResponse> mappedWorktimesList = new LinkedList<WorktimeCreationResponse>();

        createdWorktimes.forEach( newWorktime-> {
            WorktimeCreationResponse worktimeCreationResponse = new WorktimeCreationResponse();
			worktimeCreationResponse.setId(newWorktime.getId());
			worktimeCreationResponse.setComment(newWorktime.getComment());
			worktimeCreationResponse.setEndTime(newWorktime.getEndTime());
			worktimeCreationResponse.setStartTime(newWorktime.getStartTime());
			worktimeCreationResponse.setModifiedBy(newWorktime.getModifiedBy());
			worktimeCreationResponse.setType(newWorktime.getType());
			worktimeCreationResponse.setWorkdayId(newWorktime.getWorkdayId());
			mappedWorktimesList.add(worktimeCreationResponse);
        });
		return mappedWorktimesList;
	}

	private WorkdayCreationResponse responseNewWorkdayWithNewWorktimes(WorkdayCreationResponse responseWorkday, List<WorktimeCreationResponse> createdWorktimes) {
		 responseWorkday.setWorktimesCreatioenResponse(createdWorktimes);
		 return responseWorkday;
	}

	private List<Worktime> mapWorktimeCreationRequestToWorktime(WorkdayCreationRequest worktimeCreationRequest,Workday newWorkDayId) {
		List<Worktime> newWorktimes = new LinkedList<>();
		List<WorktimeCreationRequest> worktimes = worktimeCreationRequest.getWorktimesCreationRequest();

		for (WorktimeCreationRequest worktime : worktimes) {
			Worktime newWorktime = new Worktime();
			newWorktime.setComment(worktime.getComment());
			newWorktime.setEndTime(worktime.getEndTime());
			newWorktime.setStartTime(worktime.getStartTime());
			newWorktime.setModifiedBy(worktime.getModifiedBy());
			newWorktime.setType(worktime.getType());
			newWorktime.setWorkday(newWorkDayId);
			newWorktimes.add(newWorktime);
		}
		return newWorktimes;
	}




	@Override
	public WorktimesUpdateResponse saveModified(WorkTimeUpdateListRequest worktimesListRequest, long workDayid) {
		if(!worktimesListRequest.getDatasList().isEmpty()) {
			List<Worktime> updatedWorktimes = updateExistsWorkTimesAndCreatedNewWorktimesRequestMapper(worktimesListRequest,workDayid);
			List<Worktime> responseWorktimes = updateTheDatabase(updatedWorktimes);
            int numberOfDeletedRows = deleteRemovedWorktimes(updatedWorktimes,workDayid);
            List<WorktimeGetResponse> responseWorktime = worktimeMapper(responseWorktimes);
            return updateWorktimesResponseMapper(responseWorktime,numberOfDeletedRows);
		}
		else {
			int numberOfDeletedRows = deleteAllWorkTimesById(workDayid);
            return deletedAllWorktimesMapper(numberOfDeletedRows);
		}
	}

	private List<Worktime> updateExistsWorkTimesAndCreatedNewWorktimesRequestMapper(WorkTimeUpdateListRequest worktimesListRequest,long workDayId) {
		List<Worktime> updatedWorktimes = new LinkedList<>();
		List<WorkTimeUpdateRequest> worktimesList =worktimesListRequest.getDatasList();
        Optional<Workday> workday = workdayRepository.findById(workDayId);
		for (WorkTimeUpdateRequest updateDatas : worktimesList) {
			Worktime updateWorktimesWorktime = new Worktime();
			long worktimeId = updateDatas.getId();
			if(!isNewWorktime(worktimeId)) {
				updateWorktimesWorktime = isModifiedTheStartTimeAndEndTime(updateDatas,worktimeId);
			}
			updateWorktimesWorktime.setId(worktimeId);
			updateWorktimesWorktime.setComment(updateDatas.getComment());
			updateWorktimesWorktime.setEndTime(updateDatas.getEndTime());
			updateWorktimesWorktime.setStartTime(updateDatas.getStartTime());
			updateWorktimesWorktime.setModifiedBy(updateDatas.getModifiedBy());
			updateWorktimesWorktime.setType(updateDatas.getType());
			updateWorktimesWorktime.setWorkdayId(workDayId);
            updateWorktimesWorktime.setWorkday(workday.get());
			updatedWorktimes.add(updateWorktimesWorktime);
		}
		return updatedWorktimes;
	}

	private boolean isNewWorktime(long worktimeId){
		if(worktimeId==0) {
			return true;
		}
		else {
			return false;
		}
	}

	private Worktime isModifiedTheStartTimeAndEndTime(WorkTimeUpdateRequest updateDatas,long worktimeId) {
		Optional<Worktime> updateWorktime =  worktimeRepository.findById(worktimeId);
        if (!(updateWorktime.get().getStartTime().isEqual(updateDatas.getStartTime())
                && updateWorktime.get().getEndTime().isEqual(updateDatas.getEndTime()))) {
			updateWorktime.get().setModifiedStartTime(updateWorktime.get().getStartTime());
			updateWorktime.get().setModifiedEndTime(updateWorktime.get().getEndTime());
		}
		return updateWorktime.get();
	}
    
	private List<Worktime> updateTheDatabase(List<Worktime> updatedWorktimes) {
			return (List<Worktime>) worktimeRepository.saveAll(updatedWorktimes);
	}

	private int deleteRemovedWorktimes(List<Worktime> updatedWorktimes,long workDayid) {
		List<Worktime> worktimesinTheRepository = worktimeRepository.findAllByWorkdayId(workDayid);
		return deleteifIdNotExist(updatedWorktimes,worktimesinTheRepository);
	}

	private int deleteifIdNotExist(List<Worktime> updatedWorktimes,List<Worktime> worktimesinTheRepository) {
		for (Worktime updatedWorktime : updatedWorktimes) {
            worktimesinTheRepository.removeIf(workTimeInDB -> (workTimeInDB.getId() == updatedWorktime.getId()));
		}
        worktimeRepository.deleteAll(worktimesinTheRepository);
        return worktimesinTheRepository.size();
	}

    private WorktimesUpdateResponse deletedAllWorktimesMapper(int numberOfDeletedRows){
        WorktimesUpdateResponse deletedAllWorktimes = new WorktimesUpdateResponse();
        deletedAllWorktimes.setDeletedWorktimes(numberOfDeletedRows);
        return deletedAllWorktimes;
    }

    private  WorktimesUpdateResponse updateWorktimesResponseMapper( List<WorktimeGetResponse> savedWorktimes ,int numberOfDeletedRows){
        WorktimesUpdateResponse deletedAllWorktimes = new WorktimesUpdateResponse();
        deletedAllWorktimes.setDeletedWorktimes(numberOfDeletedRows);
        deletedAllWorktimes.setUpdatedWorkTimes(savedWorktimes);
        return deletedAllWorktimes;
    }
    
	@Override
	public int deleteAllWorkTimesById(long id) {
		List<Worktime> deleteWorkdays = worktimeRepository.findAllByWorkdayId(id);
		worktimeRepository.deleteAll(deleteWorkdays);
		workdayRepository.deleteById(id); //nem itt a helye
        return deleteWorkdays.size();
	}


	@Override
	public WorkTimeStatByIdResponse workTimeStatByIdResponse(long userId,int year) {
		return loadWorkTimeStat(userId,year);
	}

	private WorkTimeStatByIdResponse loadWorkTimeStat(long userId,int year) {
		if (userId!=0) {
			return oneWorkerDatas(userId,year);
		} else {
			return allWorkerDatas(userId,year);
		}
	}

	private WorkTimeStatByIdResponse oneWorkerDatas(long userId,int year) {
		List<Long> WorkdayIds =getOnlyWorkdayId(getAllUsersWorkData(userId,year));
		return worktimeStatsResponseMapper(userId,sumWorktimesByWorkid(WorkdayIds),WorkdayIds.size());
	}

	private WorkTimeStatByIdResponse allWorkerDatas(long userId,int year) {
		List<Long> WorkdayIds =getOnlyWorkdayId(getAllWorkData(year));
		return worktimeStatsResponseMapper(userId,sumWorktimesByWorkid(WorkdayIds),WorkdayIds.size());
	}

    private List<Integer> sumWorktimesByWorkid(List<Long> WorkdayIds){
        List<Worktime> worktimes = getWorkTimeByIDInSearchListYear(WorkdayIds);
		List<Integer> weeksOfTheYear = new ArrayList<>(selectitonAndSumWorkhoursWeeksofTheYear(worktimes).values());
        return weeksOfTheYear;
    }

	private List<Workday> getAllUsersWorkData(long userId,int year) {
       DateManager date = new DateManager(year);
       return workdayRepository.findByUserIdAndDateBetween((int) userId, date.getStartDate(), date.getFinishDate());
	}

	private List<Workday> getAllWorkData(int year) {
        DateManager date = new DateManager(year);
		return workdayRepository.findByDateBetween(date.getStartDate(), date.getFinishDate());
	}

	private List<Long> getOnlyWorkdayId(List<Workday> workdays){
		List<Long> onlyWorkIDList = new LinkedList<>();
        workdays.forEach((workday) -> {
            onlyWorkIDList.add(workday.getId());
        });
		return onlyWorkIDList;
	}

	private List<Worktime> getWorkTimeByIDInSearchListYear(List<Long> workdayId){
		return worktimeRepository.findAllByWorkdayIdIn(workdayId);
	}

	private Map<Integer, Integer> selectitonAndSumWorkhoursWeeksofTheYear(List<Worktime> worktimes) {
		Map<Integer, Integer> weeksOfTheYearWithWorkhoursInWeekMap = declareMap();
        worktimes.forEach((worktime) -> {
            addParametersTomap(worktime,weeksOfTheYearWithWorkhoursInWeekMap);
        });
		return weeksOfTheYearWithWorkhoursInWeekMap;
	}

	private Map<Integer, Integer> declareMap(){
		Map<Integer, Integer> newMap = new HashMap<>();
		for(int i = 1; i <= NUMBER_OF_WEEKS_IN_YEAR; i++) {
			newMap.put(i, 0);
		}
		return newMap;
	}

	private void addParametersTomap(Worktime worktime,Map<Integer, Integer> weeksOfTheYearWithWorkhoursInWeekMap) {
		int mapKey= worktime.getStartTime().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
		int tempValues = weeksOfTheYearWithWorkhoursInWeekMap.get(mapKey);
		int value = tempValues + worktime.getEndTime().getHour()- worktime.getStartTime().getHour();
		weeksOfTheYearWithWorkhoursInWeekMap.put(mapKey, value);
	}

	private WorkTimeStatByIdResponse worktimeStatsResponseMapper(Long userId, List<Integer> weeksOfTheYear,int numberOfWorkday) {
		WorkTimeStatByIdResponse workTimeStatByIdResponse = new WorkTimeStatByIdResponse();

		workTimeStatByIdResponse.setUserId(userId);
		workTimeStatByIdResponse.setNumberOfworkday(numberOfWorkday);
		workTimeStatByIdResponse.setWorktimesOfTheWeeks(weeksOfTheYear);
		int workhours= sumWorkhours(weeksOfTheYear);
		workTimeStatByIdResponse.setWorktimehours(workhours);
		workTimeStatByIdResponse.setOvertimes(calculateWorkedHours(workhours));
		return workTimeStatByIdResponse;

	}

	private int sumWorkhours(List<Integer> weeksOfTheYear) {
		return weeksOfTheYear.stream().mapToInt(i-> i).sum();
	}

	private int calculateWorkedHours(int theWorkersAllWorkhours) {
		if (theWorkersAllWorkhours>=ALL_WORKHOURS_OF_YEAR) {
			return ALL_WORKHOURS_OF_YEAR-theWorkersAllWorkhours;
		}
		else {
			return 0;
		}
	}
}
