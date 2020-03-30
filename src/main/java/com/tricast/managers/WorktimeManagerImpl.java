package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tricast.repositories.WorktimeRepository;
import com.tricast.repositories.entities.Worktime;
import com.tricast.repositories.entities.enums.WorktimeType;

@Component
public class WorktimeManagerImpl implements WorktimeManager{
	
	private WorktimeRepository worktimeRepository;

	
	
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
	public void deleteById(long id) {
		worktimeRepository.deleteById(id);
		
	}
/*
	@Override
	public List<Worktime> getAllByType(WorktimeType worktimeType) {
		return worktimeRepository.findByType(worktimeType);
	}*/

	
	
	
	
}
