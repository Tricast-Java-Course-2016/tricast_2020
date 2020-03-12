package com.tricast;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(sequenceName = "SEQ_USERS", name = "SEQ_USERS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator= "SEQ_USERS")
	private long id;
	
	private ZonedDateTime accountcreated;

	private String address;

	private String companyname;

	private String dob;

	private String email;

	private String firstname;

	private String gender;

	private Boolean isactive;

	private Timestamp lastlogin;

	private String lastname;

	private String middlename;

	private String password;

	private String phone;

	private String postcode;

	private Integer roleid;

	private String username;

	//bi-directional many-to-one association to Workday
	@OneToMany(mappedBy="user")
	private List<Workday> workdays;

	public User() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ZonedDateTime getAccountcreated() {
		return this.accountcreated;
	}

	public void setAccountcreated(ZonedDateTime accountcreated) {
		this.accountcreated = accountcreated;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
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

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public Timestamp getLastlogin() {
		return this.lastlogin;
	}

	public void setLastlogin(Timestamp lastlogin) {
		this.lastlogin = lastlogin;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return this.middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
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

	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Workday> getWorkdays() {
		return this.workdays;
	}

	public void setWorkdays(List<Workday> workdays) {
		this.workdays = workdays;
	}

	public Workday addWorkday(Workday workday) {
		getWorkdays().add(workday);
		workday.setUser(this);

		return workday;
	}

	public Workday removeWorkday(Workday workday) {
		getWorkdays().remove(workday);
		workday.setUser(null);

		return workday;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", accountcreated=" + accountcreated + ", address=" + address + ", companyname="
				+ companyname + ", dob=" + dob + ", email=" + email + ", firstname=" + firstname + ", gender=" + gender
				+ ", isactive=" + isactive + ", lastlogin=" + lastlogin + ", lastname=" + lastname + ", middlename="
				+ middlename + ", password=" + password + ", phone=" + phone + ", postcode=" + postcode + ", roleid="
				+ roleid + ", username=" + username + ", workdays=" + workdays + "]";
	}
	
	
}