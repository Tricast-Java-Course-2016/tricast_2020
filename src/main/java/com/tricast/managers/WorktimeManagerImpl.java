package com.tricast.managers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.criteria.LiteralHandlingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.requests.WorkTimeUpdateListRequest;
import com.tricast.api.requests.WorkTimeUpdateRequest;
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
		List<Worktime> updatedWorktimes = updateWorkTimesRequestMapper(worktimesListRequest,workDayid);
		List<Worktime> responseWorktimes = createdNewWorktimesAndUpdateOlds(updatedWorktimes);
		deleteRemovedWorktimes(updatedWorktimes,workDayid);
		return responseWorktimes;
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
	}
	
	
}
