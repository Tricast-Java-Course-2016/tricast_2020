package com.tricast.managers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.requests.WorkdayCreationRequest;
import com.tricast.api.requests.WorktimeCreationRequest;
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
	public Optional<Worktime> getById(long id) {
		return worktimeRepository.findById(id);
	}

	@Override
	public Iterable<Worktime> getAll(){
		return worktimeRepository.findAll();
	}
	
	@Override
	public Worktime createWorktime(Worktime worktimeRequest) {
		// TODO Auto-generated method stub
		return worktimeRepository.save(worktimeRequest);
	}
	
	@Override
	public WorkdayCreationResponse createWorkdayWithWorktimeFromRequest(WorkdayCreationRequest workdayCreationRequest) {
		
		Workday newWorkday = RequestWorkdayMapper(workdayCreationRequest);
		List<Worktime> newWorktime = mapWorktimeCreationRequestToWorktime(workdayCreationRequest);

		Workday createdWorkday = workdayRepository.save(newWorkday);
		List <Worktime> createdWorktimes = (List<Worktime>) worktimeRepository.saveAll(newWorktime);

		WorkdayCreationResponse responseWorkday = ResponsenewWorkday(createdWorkday);
		List <WorktimeCreationResponse> WorktimeCreationResponse = ResponsenewWorktimes(createdWorktimes);

		return ResponsenNewWorkdayWithWorktimes(responseWorkday,WorktimeCreationResponse);
		
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

	private List<Worktime> mapWorktimeCreationRequestToWorktime(WorkdayCreationRequest worktimeCreationRequest) {
		List<Worktime> newWorktimes = new LinkedList<Worktime>();
		Worktime newWorktime = new Worktime();
		List<WorktimeCreationRequest> worktimes = worktimeCreationRequest.getWorktimesCreationRequest();
		for (WorktimeCreationRequest worktime : worktimes) {
			newWorktime.setComment(worktime.getComment());
			newWorktime.setEndTime(worktime.getEndTime());
			newWorktime.setStartTime(worktime.getStartTime());
			newWorktime.setModifiedBy(worktime.getModifiedBy());
			newWorktime.setType(worktime.getType());
			newWorktime.setWorkdayId(worktime.getWorkdayId());
			newWorktime.setWorkdayId(worktime.getWorkdayId());
			newWorktimes.add(newWorktime);
		}
		return newWorktimes;
	}
	
	
	
	
	
	@Override
	public void deleteById(long id) {
		worktimeRepository.deleteById(id);
		
	}
	
	@Override
	public void deleteAllWorkTimesById(long id) {
		List<Worktime>deleteWorktimes = worktimeRepository.findAllByWorkdayId(id);
		worktimeRepository.deleteAll(deleteWorktimes);	
	}

	
	/*
	@Override
	public List<Worktime> getAllByType(WorktimeType worktimeType) {
		return worktimeRepository.findByType(worktimeType);
	}*/

	private WorktimeCreationResponse mapWorktimeToWorktimeCreationResponse(Worktime worktime) {
		WorktimeCreationResponse createdWorktime = new WorktimeCreationResponse();
		createdWorktime.setId(worktime.getId());
		createdWorktime.setComment(worktime.getComment());
		createdWorktime.setEndTime(worktime.getEndTime());
		createdWorktime.setStartTime(worktime.getStartTime());
		createdWorktime.setModifiedBy(worktime.getModifiedBy());
		createdWorktime.setType(worktime.getType());
		createdWorktime.setWorkdayId(worktime.getWorkdayId());
		
		return createdWorktime;
	}
	
	
}
