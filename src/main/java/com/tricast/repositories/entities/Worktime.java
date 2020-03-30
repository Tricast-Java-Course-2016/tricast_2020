package com.tricast.repositories.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.tricast.repositories.entities.enums.WorktimeType;

import java.sql.Timestamp;


/**
 * The persistent class for the worktimes database table.
 * 
 */
@Entity
@Table(name="worktimes")
@NamedQuery(name="Worktime.findAll", query="SELECT w FROM Worktime w")
public class Worktime implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="WORKTIMES_ID_GENERATOR", sequenceName="SEQ_WORKTIMES")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="WORKTIMES_ID_GENERATOR")
	@Column(name="id")
	private long id;

	@Column(name="comment")
	private String comment;

	@Column(name="endtime")
	private Timestamp endTime;

	@Column(name="modifiedby")
	private Integer modifiedBy;

	@Column(name="modifiedendtime")
	private Timestamp modifiedEndTime;

	@Column(name="modifiedstarttime")
	private Timestamp modifiedStartTime;

	@Column(name="starttime")
	private Timestamp startTime;

	@Enumerated(EnumType.STRING)
	@Column(name="type")
	private WorktimeType type;

	//bi-directional many-to-one association to Workday
	/*@ManyToOne
	@JoinColumn(name="workdayid")
	private Workday workday;*/

	@Column(name="workdayid")
	private long workdayId;
	
	public Worktime() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
/*
	public Timestamp getModifiedstarttime() {
		return this.modifiedStartTime;
	}

	public void setModifiedstarttime(Timestamp modifiedstarttime) {
		this.modifiedStartTime = modifiedstarttime;
	}
*/
	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedEndTime() {
		return modifiedEndTime;
	}

	public void setModifiedEndTime(Timestamp modifiedEndTime) {
		this.modifiedEndTime = modifiedEndTime;
	}
//
	public Timestamp getModifiedStartTime() {
		return modifiedStartTime;
	}
//
	public void setModifiedStartTime(Timestamp modifiedStartTime) {
		this.modifiedStartTime = modifiedStartTime;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public WorktimeType getType() {
		return this.type;
	}

	public void setType(WorktimeType type) {
		this.type = type;
	}
	
	public long getWorkdayId() {
		return this.workdayId;
	}
	
	public void setWorkdayId(long workdayId) {
		this.workdayId = workdayId;
	}
	
	/*
	public Workday getWorkday() {
		return this.workday;
	}

	public void setWorkday(Workday workday) {
		this.workday = workday;
	}*/

}