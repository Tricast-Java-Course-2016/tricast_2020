package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.repositories.RolePermissionSetRepository;
import com.tricast.repositories.entities.RolePermissionSet;

@Component
public class RolePermissionSetManagerImpl implements RolePermissionSetManager{

	private RolePermissionSetRepository rolePermissionSetRepository;
	
	@Autowired
	public RolePermissionSetManagerImpl(RolePermissionSetRepository rolePermissionSetRepository) {
		this.rolePermissionSetRepository = rolePermissionSetRepository;
	}
	
	@Override
	public Optional<RolePermissionSet> getById(long id) {
		return rolePermissionSetRepository.findById(id);
	}

	@Override
	public RolePermissionSet createRolePermissionSet(RolePermissionSet rolePErmissionSetRequest) {
		return rolePermissionSetRepository.save(rolePErmissionSetRequest);
	}
	
	@Override
	public List<RolePermissionSet> getAll() {
		return (List<RolePermissionSet>) rolePermissionSetRepository.findAll();
	}

	@Override
	public void deleteRolePermissionSet(long id) {
		rolePermissionSetRepository.deleteById((long) id);		
	}

	@Override
	public RolePermissionSet updateRolePermissionSet(RolePermissionSet rolePermissionSetRequest) {
		return rolePermissionSetRepository.save(rolePermissionSetRequest);
	}

}
