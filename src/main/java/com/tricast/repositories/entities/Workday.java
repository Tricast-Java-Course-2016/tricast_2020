package com.tricast.repositories.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the workdays database table.
 * 
 */
@Entity
@Table(name="workdays")
@NamedQuery(name="Workday.findAll", query="SELECT w FROM Workday w")
public class Workday implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="WORKDAYS_ID_GENERATOR", sequenceName="SEQ_WORKDAYS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="WORKDAYS_ID_GENERATOR")
	@Column(name="id")
	private long id;
	
	@Column(name="date")
	private ZonedDateTime date;
	
	@Column(name="userid")
	private Integer userId;

	
	@OneToMany
	private List<Worktime> worktimes;
	
	public Workday() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

    public List<Worktime> getWorktimes() {
        return worktimes;
    }

    public void setWorktimes(List<Worktime> worktimes) {
        this.worktimes = worktimes;
    }

}