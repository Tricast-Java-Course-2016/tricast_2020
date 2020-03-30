package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import com.tricast.repositories.entities.Worktime;
import com.tricast.repositories.entities.enums.WorktimeType;

public interface WorktimeManager {
	Optional<Worktime> getById (long id);
	
	Iterable<Worktime> getAll();
	
	Worktime createWorktime(Worktime worktimeRequest);
	
	void deleteById(long id);
	
	//List<Worktime> getAllByType(WorktimeType worktype);

}
