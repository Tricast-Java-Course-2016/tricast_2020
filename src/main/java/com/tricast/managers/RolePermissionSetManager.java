package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import com.tricast.repositories.entities.RolePermissionSet;
import com.tricast.repositories.entities.User;

public interface RolePermissionSetManager {
	Optional<RolePermissionSet> getById(long id);
	
	RolePermissionSet createRolePermissionSet(RolePermissionSet rolePErmissionSetRequest);
	
	RolePermissionSet updateRolePermissionSet(RolePermissionSet rolePermissionSetRequest);
	
	List<RolePermissionSet> getAll();
	
	void deleteRolePermissionSet(long id);
}
