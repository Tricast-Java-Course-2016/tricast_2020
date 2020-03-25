package com.tricast.repositories.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tricast.repositories.entities.enums.UserGender;

/**
 * The persistent class for the users database table.
 *
 */
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_USERS", sequenceName = "SEQ_USERS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USERS")
	private long id;

	@Column(name = "accountcreated")
	private ZonedDateTime accountCreated;

	@Column(name = "address")
	private String address;

	@Column(name = "companyname")
	private String companyName;

	@Column(name = "dob")
	private String dob;

	@Column(name = "email")
	private String email;

	@Column(name = "firstname")
	private String firstName;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private UserGender gender;

	@Column(name = "isactive")
	private Boolean isActive;

	@Column(name = "lastlogin")
	private ZonedDateTime lastLogin;

	@Column(name = "lastname")
	private String lastName;

	@Column(name = "middlename")
	private String middleName;

	@Column(name = "password")
	private String password;

	@Column(name = "phone")
	private String phone;

	@Column(name = "postcode")
	private String postcode;

	@Column(name = "roleid")
	private Integer roleId;

	@Column(name = "username")
	private String userName;

	public User() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ZonedDateTime getAccountCreated() {
		return this.accountCreated;
	}

	public void setAccountCreated(ZonedDateTime accountCreated) {
		this.accountCreated = accountCreated;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDob() {
		return this.dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public UserGender getGender() {
		return this.gender;
	}

	public void setGender(UserGender gender) {
		this.gender = gender;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public ZonedDateTime getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(ZonedDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}