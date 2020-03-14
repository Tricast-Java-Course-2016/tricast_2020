package com.tricast.repositories;

import org.springframework.data.repository.CrudRepository;

import com.tricast.repositories.entities.Workday;

public interface WorkdayRepository extends CrudRepository<Workday, Long> {

}
