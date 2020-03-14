package com.tricast.repositories;

import org.springframework.data.repository.CrudRepository;

import com.tricast.repositories.entities.Worktime;

public interface WorktimeRepository extends CrudRepository<Worktime, Long> {

}
