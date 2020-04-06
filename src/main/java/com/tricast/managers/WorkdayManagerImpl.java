package com.tricast.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.api.requests.WorktimeCreationRequest;
import com.tricast.api.responses.WorkdayCreationResponse;
import com.tricast.api.responses.WorkdayGetResponse;
import com.tricast.api.responses.WorkdayWorktimesGetResponse;
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
	public List<WorkdayGetResponse> getAllWorkdayByUserIdAndMonth() {
		long userId = 1;
		int month = 4;
		
		List<Workday> allWorkdays = (List<Workday>)workdayRepository.findAll();
		List<WorkdayGetResponse> allWorkdaysGetResponse = new ArrayList<WorkdayGetResponse>();
		
		for (Workday w : allWorkdays) {
			if (userId == w.getUserId() && month == w.getDate().getMonthValue()) {
				WorkdayGetResponse response = new WorkdayGetResponse();
				response.setId(w.getId());
				response.setDate(w.getDate());
				response.setUserId(w.getUserId());
				
				allWorkdaysGetResponse.add(response);
			}
		}
		
		return allWorkdaysGetResponse;
	}
	
	
	
	
	//Get a given workday data and it's associated worktimes
	@Override
	public WorkdayWorktimesGetResponse getUserWorktimesByDate(String date) {
		List<Workday> workdays = (List<Workday>)workdayRepository.findAll();

		long userId = 1;
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(4, 6));
		int day = Integer.parseInt(date.substring(6, 8));
		
		List<Worktime> worktimes = (List<Worktime>)worktimeRepository.findAll();		
		List<WorktimeGetResponse> workimeResponses = new ArrayList<WorktimeGetResponse>();
		
		for (Workday w : workdays) {
			if (w.getUserId() == userId && w.getDate().getYear()== year && w.getDate().getMonthValue() == month && w.getDate().getDayOfMonth() == day) {
				/*WorkdayWorktimesGetResponse workdayWorktimesGetResponse = new WorkdayWorktimesGetResponse();
				workdayWorktimesGetResponse.setId(w.getId());
				workdayWorktimesGetResponse.setDate(w.getDate());
				workdayWorktimesGetResponse.setUserId(w.getUserId());*/
				
				for(Worktime wt : worktimes) {
					if (wt.getWorkdayId() == w.getId()) {
						WorktimeGetResponse newWorktimeGetResponse = mapWorktimeToWorktimeGetResponse(wt);
						workimeResponses.add(newWorktimeGetResponse);
					}
				}
				
				//workdayWorktimesGetResponse.setWorktimes(workimeResponses);
				WorkdayWorktimesGetResponse workdayWorktimesGetResponse = mapWorkdayWorktimesToWorkdayWorktimesGetResponse(w, workimeResponses);
				return workdayWorktimesGetResponse;
			}
		}
		
		return null;
	}
	
	private WorkdayWorktimesGetResponse mapWorkdayWorktimesToWorkdayWorktimesGetResponse(Workday workday, List<WorktimeGetResponse> worktimesGetResponse) {
		WorkdayWorktimesGetResponse createdWorkdayWorktimesGetResponse = new WorkdayWorktimesGetResponse();
		createdWorkdayWorktimesGetResponse.setId(workday.getId());
		createdWorkdayWorktimesGetResponse.setDate(workday.getDate());
		createdWorkdayWorktimesGetResponse.setUserId(workday.getUserId());
		createdWorkdayWorktimesGetResponse.setWorktimes(worktimesGetResponse);
		return createdWorkdayWorktimesGetResponse;
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








	
	
	/*//Old
	@Override
	public WorkdayCreationResponse createWorkdayFromRequest(WorkdayCreationRequest workdayCreationRequest) {
		Workday newWorkday = mapWorkdayCreatioonRequestToWorkday(workdayCreationRequest);
		Workday createdWorkday = workdayRepository.save(newWorkday);
		return mapWorkdayToWorkdayCreationResponse(createdWorkday);
	}

	private Workday mapWorkdayCreatioonRequestToWorkday(WorkdayCreationRequest workdayCreationRequest) {
		Workday newWorkday = new Workday();
		newWorkday.setDate(workdayCreationRequest.getDate());
		newWorkday.setUserId(workdayCreationRequest.getUserId());
		return newWorkday;
	}
	
	private WorkdayCreationResponse mapWorkdayToWorkdayCreationResponse(Workday workday) {
		WorkdayCreationResponse createdWorkday = new WorkdayCreationResponse();
		createdWorkday.setId(workday.getId());
		createdWorkday.setDate(workday.getDate());
		createdWorkday.setUserId(workday.getUserId());
		return createdWorkday;
	}
	*/
	
}
