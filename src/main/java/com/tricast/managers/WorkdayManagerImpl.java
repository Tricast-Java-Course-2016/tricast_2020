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
	
	private WorktimeRepository worktimeRepository;
	
	@Autowired
	public WorkdayManagerImpl(WorkdayRepository workdayRepository, WorktimeRepository worktimeRepository) {
		this.workdayRepository = workdayRepository;
		this.worktimeRepository = worktimeRepository;
	}
	
	@Override
	public Optional<Workday> getById(long id){
		return workdayRepository.findById(id);
	}

	@Override
	public Workday createWorkday(Workday workdayRequest) {
		// TODO Auto-generated method stub
		return workdayRepository.save(workdayRequest);
	}
	
	@Override
	public Workday updateWorkday(Workday workdayRequest, long id) {
		// TODO Auto-generated method stub
		Workday updatedWorkday = workdayRequest;
		updatedWorkday.setId(id);
		
		return workdayRepository.save(updatedWorkday);
	}
	

	
	@Override
	public void deleteById(long id) {
		List<Worktime> worktimes = (List<Worktime>)worktimeRepository.findAll();
		
		System.out.println(worktimes);
		
		for (Worktime w : worktimes) {
			if (w.getWorkdayId() == id) {
				//long worktimeId = w.getId();
				worktimeRepository.deleteById(w.getId());
			}
		}
		
		
		workdayRepository.deleteById(id);
	}
	
	
	
	@Override
	public Iterable<Workday> getAll (){
		return workdayRepository.findAll();
	}

	
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

	
	private WorktimeGetResponse mapWorktimeToWorktimeGetResponse(Worktime worktime) {
		WorktimeGetResponse createdWorktimeGetResponse = new WorktimeGetResponse();
		
		createdWorktimeGetResponse.setId(worktime.getId());
		createdWorktimeGetResponse.setComment(worktime.getComment());
		createdWorktimeGetResponse.setEndTime(worktime.getEndTime());
		createdWorktimeGetResponse.setModifiedBy(worktime.getModifiedBy());
		createdWorktimeGetResponse.setStartTime(worktime.getStartTime());
		createdWorktimeGetResponse.setWorkdayId(worktime.getWorkdayId());
		createdWorktimeGetResponse.setModifiedEndTime(worktime.getModifiedEndTime());
		createdWorktimeGetResponse.setModifiedStartTime(worktime.getModifiedStartTime());
		createdWorktimeGetResponse.setType(worktime.getType());
		
		return createdWorktimeGetResponse;
	}
	
	@Override
	public WorkdayCreationResponse updateUserWorkdayByDate(WorkdayCreationRequest workdayCreationRequest, String day) {
		Workday newWorkday = mapWorkdayCreatioonRequestToWorkday(workdayCreationRequest);
		
		
		return null;
	}
	
	//New WorkdayCreation
	@Override
	public WorkdayCreationResponse createWorkdayFromRequest(WorkdayCreationRequest workdayCreationRequest) {
		Workday newWorkday = mapWorkdayCreatioonRequestToWorkday(workdayCreationRequest);
		Workday createdWorkday = workdayRepository.save(newWorkday);
		
		List<WorktimeCreationRequest> newWorktimeCreationRequests = workdayCreationRequest.getWorktimesCreationRequest();
		List<Worktime> createdWorktimes = new ArrayList<Worktime>();
		for (WorktimeCreationRequest worktimeCreationRequest : newWorktimeCreationRequests) {
			Worktime newWorktime = mapWorktimeCreationRequestToWorktime(worktimeCreationRequest);
			Worktime createdWorktime = worktimeRepository.save(newWorktime);
			createdWorktimes.add(createdWorktime);
		}
		
		return mapWorkdayToWorkdayCreationResponse(createdWorkday, createdWorktimes);
	}

	private Worktime mapWorktimeCreationRequestToWorktime(WorktimeCreationRequest worktimeCreationRequest) {
		Worktime newWorktime = new Worktime();
		newWorktime.setComment(worktimeCreationRequest.getComment());
		newWorktime.setEndTime(worktimeCreationRequest.getEndTime());
		newWorktime.setModifiedBy(worktimeCreationRequest.getModifiedBy());
		newWorktime.setStartTime(worktimeCreationRequest.getStartTime());
		newWorktime.setType(worktimeCreationRequest.getType());
		newWorktime.setWorkdayId(worktimeCreationRequest.getWorkdayId());
		
		return newWorktime;
	}
	
	private Workday mapWorkdayCreatioonRequestToWorkday(WorkdayCreationRequest workdayCreationRequest) {
		
		Workday newWorkday = new Workday();
		newWorkday.setDate(workdayCreationRequest.getDate());
		newWorkday.setUserId(workdayCreationRequest.getUserId());
		
		return newWorkday;
	}
	
	private WorkdayCreationResponse mapWorkdayToWorkdayCreationResponse(Workday workday, List<Worktime> worktimes) {
		WorkdayCreationResponse createdWorkday = new WorkdayCreationResponse();
		
		//Workday
		
		createdWorkday.setId(workday.getId());
		createdWorkday.setDate(workday.getDate());
		createdWorkday.setUserId(workday.getUserId());
		
		//Worktimes
		List<WorktimeCreationResponse> createdWorktimes = new ArrayList<WorktimeCreationResponse>();
		
		for (Worktime w : worktimes) {
			WorktimeCreationResponse createdWorktime = new WorktimeCreationResponse();
			createdWorktime.setId(w.getId());
			createdWorktime.setComment(w.getComment());
			createdWorktime.setEndTime(w.getEndTime());
			createdWorktime.setModifiedBy(w.getModifiedBy());
			createdWorktime.setStartTime(w.getStartTime());
			createdWorktime.setWorkdayId(w.getWorkdayId());
			createdWorktime.setType(w.getType());
			
			createdWorktimes.add(createdWorktime);
		}
		
		createdWorkday.setWorktimesCreatioenResponse(createdWorktimes);
		
		return createdWorkday;
	}
	
}
