package com.tricast.managers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.repositories.WorkdayRepository;
import com.tricast.repositories.WorktimeRepository;
import com.tricast.repositories.entities.Workday;

@Component
public class WorkdayManagerImpl implements WorkdayManager{
	
	private WorkdayRepository workdayRepository;
	
	private WorktimeRepository worktimeRepository;
	
	@Autowired
	public WorkdayManagerImpl(WorkdayRepository workdayRepository) {
		this.workdayRepository = workdayRepository;
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
	public void deleteById(long id) {
		workdayRepository.deleteById(id);
	}
	
	@Override
	public Iterable<Workday> getAll (){
		return workdayRepository.findAll();
	}
	
	
}
