package com.tricast.repositories.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rolepermissionset database table.
 * 
 */
@Entity
@Table(name="rolePermissionSet")
@NamedQuery(name="Rolepermissionset.findAll", query="SELECT r FROM Rolepermissionset r")
public class RolePermissionSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ROLEPERMISSIONSET", sequenceName = "SEQ_ROLEPERMISSIONSET")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ROLEPERMISSIONSET")
	private long id;

	@Column(name = "permissionid")
	private Integer permissionId;
	
	@Column(name = "roleid")
	private Integer roleId;

	public RolePermissionSet() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getPermissionid() {
		return this.permissionId;
	}

	public void setPermissionid(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public Integer getRoleid() {
		return this.roleId;
	}

	public void setRoleid(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "RolePermissionSet [id=" + id + ", permissionId=" + permissionId + ", roleId=" + roleId + "]";
	}

	
}