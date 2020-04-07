package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.requests.WorktimeCreationRequest;
import com.tricast.api.responses.WorktimeCreationResponse;
import com.tricast.repositories.WorktimeRepository;
import com.tricast.repositories.entities.Worktime;

@Component
public class WorktimeManagerImpl implements WorktimeManager{
	
	private WorktimeRepository worktimeRepository;

	
	@Autowired
	public WorktimeManagerImpl(WorktimeRepository worktimeRepository) {
		this.worktimeRepository = worktimeRepository;
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
	public WorktimeCreationResponse createWorktimeFromRequest(WorktimeCreationRequest worktimeCreationRequest) {
		Worktime newWorktime = mapWorktimeCreationRequestToWorktime(worktimeCreationRequest);
		Worktime createdWorktime = worktimeRepository.save(newWorktime);
		return mapWorktimeToWorktimeCreationResponse(createdWorktime);
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
	
	private Worktime mapWorktimeCreationRequestToWorktime(WorktimeCreationRequest worktimeCreationRequest) {
		Worktime newWorktime = new Worktime();
		newWorktime.setComment(worktimeCreationRequest.getComment());
		newWorktime.setEndTime(worktimeCreationRequest.getEndTime());
		newWorktime.setStartTime(worktimeCreationRequest.getStartTime());
		newWorktime.setModifiedBy(worktimeCreationRequest.getModifiedBy());
		newWorktime.setType(worktimeCreationRequest.getType());
		newWorktime.setWorkdayId(worktimeCreationRequest.getWorkdayId());
		
		return newWorktime;
	}

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
