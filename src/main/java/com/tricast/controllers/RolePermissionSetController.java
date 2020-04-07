package com.tricast.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tricast.repositories.entities.RolePermissionSet;
import com.tricast.managers.RolePermissionSetManager;

@RestController
@RequestMapping(path="rest/rolepremissionset")
public class RolePermissionSetController {

	@Autowired
	private RolePermissionSetManager rolePermissionSetManager;
	
	@GetMapping(path = "/{id}")
	public Optional<RolePermissionSet> getById(@PathVariable("id") long id) {
		return rolePermissionSetManager.getById(id);
	}
	
	@PostMapping
	public RolePermissionSet saveRolePermissionSet(@RequestBody RolePermissionSet rolePermissionSetRequest) {
		return rolePermissionSetManager.createRolePermissionSet(rolePermissionSetRequest);
	}
	
	@PutMapping
	public RolePermissionSet updateRolePermissionSet(@RequestBody RolePermissionSet rolePermissionSetRequest) {
		return rolePermissionSetManager.updateRolePermissionSet(rolePermissionSetRequest);
	}
	
	@GetMapping
	public List<RolePermissionSet> getAll(){
		return rolePermissionSetManager.getAll();
	}
	
	@DeleteMapping()
	public void deleteRolePermissionSet(@RequestBody long id) {
		rolePermissionSetManager.deleteRolePermissionSet(id);
	}
}
