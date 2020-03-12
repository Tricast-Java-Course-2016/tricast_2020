package com.tricast.repositories;



import org.springframework.data.repository.CrudRepository;

import com.tricast.repositories.entities.User;
				  
public interface UserRepository extends CrudRepository<User, Long>{

	
}
