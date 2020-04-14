package com.tricast.managers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
import com.tricast.repositories.WorkdayRepository;
import com.tricast.repositories.WorktimeRepository;
import com.tricast.repositories.entities.Workday;
import com.tricast.repositories.entities.Worktime;

@Component
public class WorktimeManagerImpl implements WorktimeManager{
	
	private WorktimeRepository worktimeRepository;
	private WorkdayRepository workdayRepository;
	
	@Autowired
	public WorktimeManagerImpl(WorktimeRepository worktimeRepository, WorkdayRepository workdayRepository) {
		this.worktimeRepository = worktimeRepository;
		this.workdayRepository = workdayRepository;
	}

	@Override
	public List<Worktime> getAllWorktimeByWorktimeId(long id){
		return worktimeRepository.findAllByWorkdayId(id);
	}
	

	
	@Override
	public WorkdayCreationResponse createWorkdayWithWorktimeFromRequest(WorkdayCreationRequest workdayCreationRequest) {
		
		Workday newWorkday = RequestWorkdayMapper(workdayCreationRequest);
		Workday createdWorkday = workdayRepository.save(newWorkday);
		
		int WorkdayId = (int) createdWorkday.getId();
		List<Worktime> newWorktime = mapWorktimeCreationRequestToWorktime(workdayCreationRequest,WorkdayId);
		List <Worktime> createdWorktimes = (List<Worktime>) worktimeRepository.saveAll(newWorktime);

		WorkdayCreationResponse responseWorkday = ResponsenewWorkday(createdWorkday);
		List <WorktimeCreationResponse> worktimeCreationResponse = ResponsenewWorktimes(createdWorktimes);
		return ResponsenNewWorkdayWithWorktimes(responseWorkday,worktimeCreationResponse);
		
	}
	
	private Workday RequestWorkdayMapper(WorkdayCreationRequest workdayCreationRequest) {
		Workday newWorkDay = new Workday();
		newWorkDay.setDate(workdayCreationRequest.getDate());
		newWorkDay.setUserId(workdayCreationRequest.getUserId());
		return newWorkDay;
	}
	
	private WorkdayCreationResponse ResponsenewWorkday(Workday createdWorkday) {
		WorkdayCreationResponse workdayCreationResponse = new WorkdayCreationResponse();
		workdayCreationResponse.setDate(createdWorkday.getDate());
		workdayCreationResponse.setId(createdWorkday.getId());
		workdayCreationResponse.setUserId(createdWorkday.getUserId());
		return workdayCreationResponse;
	}
	
	private List<WorktimeCreationResponse> ResponsenewWorktimes(List <Worktime> createdWorktimes) {
		
		List<WorktimeCreationResponse> responseWorktimes = new LinkedList<WorktimeCreationResponse>();
		WorktimeCreationResponse worktimeCreationResponse = new WorktimeCreationResponse();
		
		for (Worktime newWorktimes : createdWorktimes) {
			worktimeCreationResponse.setId(newWorktimes.getId());
			worktimeCreationResponse.setComment(newWorktimes.getComment());
			worktimeCreationResponse.setEndTime(newWorktimes.getEndTime());
			worktimeCreationResponse.setStartTime(newWorktimes.getStartTime());
			worktimeCreationResponse.setModifiedBy(newWorktimes.getModifiedBy());
			worktimeCreationResponse.setType(newWorktimes.getType());
			worktimeCreationResponse.setWorkdayId(newWorktimes.getWorkdayId());
			worktimeCreationResponse.setWorkdayId(newWorktimes.getWorkdayId());
			responseWorktimes.add(worktimeCreationResponse);
		}
		return responseWorktimes;
	}

	private WorkdayCreationResponse ResponsenNewWorkdayWithWorktimes(WorkdayCreationResponse responseWorkday, List<WorktimeCreationResponse> createdWorktimes) {
		 responseWorkday.setWorktimesCreatioenResponse(createdWorktimes);
		 return responseWorkday;
	}

	private List<Worktime> mapWorktimeCreationRequestToWorktime(WorkdayCreationRequest worktimeCreationRequest,int newWorkDayId) {
		List<Worktime> newWorktimes = new LinkedList<Worktime>();
		List<WorktimeCreationRequest> worktimes = worktimeCreationRequest.getWorktimesCreationRequest();
		
		for (WorktimeCreationRequest worktime : worktimes) {
			Worktime newWorktime = new Worktime();
			newWorktime.setComment(worktime.getComment());
			newWorktime.setEndTime(worktime.getEndTime());
			newWorktime.setStartTime(worktime.getStartTime());
			newWorktime.setModifiedBy(worktime.getModifiedBy());
			newWorktime.setType(worktime.getType());
			newWorktime.setWorkdayId(newWorkDayId);
			newWorktimes.add(newWorktime);
		}
		return newWorktimes;
	}


	

	@Override
	public List<Worktime> saveModified(WorkTimeUpdateListRequest worktimesListRequest, long workDayid) {
		if(!worktimesListRequest.getDatasList().isEmpty()) {
			List<Worktime> updatedWorktimes = updateWorkTimesRequestMapper(worktimesListRequest,workDayid);
			List<Worktime> responseWorktimes = createdNewWorktimesAndUpdateOlds(updatedWorktimes);
			deleteRemovedWorktimes(updatedWorktimes,workDayid);
			return responseWorktimes;
		}
		else {
			deleteAllWorkTimesById(workDayid);
			return null;
		}
	}
	
	private List<Worktime> updateWorkTimesRequestMapper(WorkTimeUpdateListRequest worktimesListRequest,long workDayid) {
		List<Worktime> updatedWorktimes = new LinkedList<Worktime>();
		List<WorkTimeUpdateRequest> worktimesList =worktimesListRequest.getDatasList();
		
		for (WorkTimeUpdateRequest updateDatas : worktimesList) {
			Worktime updateWorktimesWorktime = new Worktime();
			long workId = updateDatas.getId();
			if(!isNewWorktime(workId)) {
				updateWorktimesWorktime = isModifiedTheStartTimeAndEndTime(updateDatas,workId);
			}
			updateWorktimesWorktime.setId(updateDatas.getId());
			updateWorktimesWorktime.setComment(updateDatas.getComment());
			updateWorktimesWorktime.setEndTime(updateDatas.getEndTime()); 
			updateWorktimesWorktime.setStartTime(updateDatas.getStartTime()); 
			updateWorktimesWorktime.setModifiedBy(updateDatas.getModifiedBy());
			updateWorktimesWorktime.setType(updateDatas.getType());
			updateWorktimesWorktime.setWorkdayId(updateDatas.getWorkdayId());
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
		if (updateWorktime.get().getStartTime().equals(updateDatas.getStartTime())  && updateWorktime.get().getEndTime().equals(updateDatas.getEndTime())) {
			updateWorktime.get().setModifiedStartTime(updateWorktime.get().getStartTime());
			updateWorktime.get().setModifiedEndTime(updateWorktime.get().getEndTime());
		}
		return updateWorktime.get();
	}
	

	private List<Worktime> createdNewWorktimesAndUpdateOlds(List<Worktime> updatedWorktimes) {
			return (List<Worktime>) worktimeRepository.saveAll(updatedWorktimes);
	}
	
	private void deleteRemovedWorktimes(List<Worktime> updatedWorktimes,long workDayid) {
		List<Worktime> WorktimesinTheRepository = worktimeRepository.findAllByWorkdayId(workDayid);
		List<Long> onlyWorktimesId = new LinkedList<Long>();
		for (Worktime worktime : WorktimesinTheRepository) {
			onlyWorktimesId.add(worktime.getId());
		}
		ifIdDidNotExistDelete(updatedWorktimes,onlyWorktimesId);
		
	}
	
	private void ifIdDidNotExistDelete(List<Worktime> updatedWorktimes,List<Long> onlyWorktimesId) {
		for (Worktime worktime : updatedWorktimes) {
				onlyWorktimesId.removeIf(n -> (n == worktime.getId()));
		}
		
		for (Long id : onlyWorktimesId) {
			worktimeRepository.deleteById(id);
		}
		
		
	}

	@Override
	public void deleteAllWorkTimesById(long id) {
		List<Worktime> deleteWorkdays = worktimeRepository.findAllByWorkdayId(id);
		worktimeRepository.deleteAll(deleteWorkdays);
		workdayRepository.deleteById(id);
	}

	
	@Override
	public WorkTimeStatByIdResponse WorkTimeStatByIdResponse(long id,int year) {
		return checkTheID(id,year);
	}
	
	private WorkTimeStatByIdResponse checkTheID(long userId,int year) {
		if (userId!=0) {
			return needOneWorkerDatas(userId,year);
		} else {
			return needAllWorkerDatas(userId,year);
		}
	}
	
	private WorkTimeStatByIdResponse needOneWorkerDatas(long userId,int year) {
		List<Long> WorkdayIds =getOnlyWorkdayId(getAllUsersWorkData(userId,year));
		List<Worktime> worktimes = getWorkTimeByIDInSearchListYear(WorkdayIds);
		List<Integer> weeksOfTheYear = new ArrayList<Integer>(selectitonAndSumWorkhoursWeeksofTheYear(worktimes).values());
		return worktimeStatsResponseMapper(userId,weeksOfTheYear,WorkdayIds.size());
	}
	
	private WorkTimeStatByIdResponse needAllWorkerDatas(long userId,int year) {
		List<Long> WorkdayIds =getOnlyWorkdayId(getAllWorkData(year));
		List<Worktime> worktimes = getWorkTimeByIDInSearchListYear(WorkdayIds);
		List<Integer> weeksOfTheYear = new ArrayList<Integer>(selectitonAndSumWorkhoursWeeksofTheYear(worktimes).values());
		return worktimeStatsResponseMapper(userId,weeksOfTheYear,WorkdayIds.size());
	}
	
	private List<Workday> getAllUsersWorkData(long userId,int year) {
		return workdayRepository.findByUserIdAndDateBetween((int) userId, createFirstDayofTheYear(year), createLastDayofTheYear(year));
	}
	
	private List<Workday> getAllWorkData(int year) {
		return workdayRepository.findByDateBetween(createFirstDayofTheYear(year), createLastDayofTheYear(year));
	}
	
	private ZonedDateTime createFirstDayofTheYear(int year) {
		return ZonedDateTime.of(year, 1, 1, 0, 0, 0, 000, ZoneId.systemDefault());
	}
	
	private ZonedDateTime createLastDayofTheYear(int year) {
		return ZonedDateTime.of(year, 12, 31, 0, 0, 0, 000, ZoneId.systemDefault());
	}
	
	private List<Long> getOnlyWorkdayId(List<Workday> workdays){
		List<Long> onlyWorkIDList = new LinkedList<Long>();
		for (Workday workday : workdays) {
			onlyWorkIDList.add(workday.getId());
		}
		return onlyWorkIDList;
	}
	
	private List<Worktime> getWorkTimeByIDInSearchListYear(List<Long> workdayId){
		return worktimeRepository.findAllByWorkdayIdIn(workdayId);
	}
	
	private Map<Integer, Integer> selectitonAndSumWorkhoursWeeksofTheYear(List<Worktime> worktimes) {
		Map<Integer, Integer> weeksOfTheYearWithWorkhoursInWeekMap = declareMap();
		for (Worktime worktime : worktimes) {
			addParametersTomap(worktime,weeksOfTheYearWithWorkhoursInWeekMap);
		}
		return weeksOfTheYearWithWorkhoursInWeekMap;
	}
	
	private Map<Integer, Integer> declareMap(){
		Map<Integer, Integer> newMap = new HashMap<Integer, Integer>();
		for(int i =1 ;i<54;i++) {
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
		workTimeStatByIdResponse.setOvertimes(DoWorkerHaveOvertimes(workhours));
		return workTimeStatByIdResponse;
		
	}
	
	private int sumWorkhours(List<Integer> weeksOfTheYear) {
		return weeksOfTheYear.stream().mapToInt(i-> i).sum();
	}
	
	private int DoWorkerHaveOvertimes(int workhours) {
		if (workhours>=1920) {
			return 1920-workhours;
		}
		else {
			return 0;
		}
	}
}
