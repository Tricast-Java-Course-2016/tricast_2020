package com.tricast.repositories.entities;

import java.io.Serializable;
import java.sql.Timestamp;
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

import com.tricast.repositories.entities.enums.WorktimeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


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
	private ZonedDateTime endTime;

	@Column(name="modifiedby")
	private Integer modifiedBy;

	@Column(name="modifiedendtime")
	private ZonedDateTime modifiedEndTime;

	@Column(name="modifiedstarttime")
	private ZonedDateTime modifiedStartTime;

	@Column(name="starttime")
	private ZonedDateTime startTime;

	@Enumerated(EnumType.STRING)
	@Column(name="type")
	private WorktimeType type;

    @ManyToOne
    @JoinColumn(name = "workdayid")
    private Workday workday;

	@Column(name = "workdayid",updatable = false, insertable = false)
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
	public ZonedDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(ZonedDateTime endTime) {
		this.endTime = endTime;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public ZonedDateTime getModifiedEndTime() {
		return modifiedEndTime;
	}

	public void setModifiedEndTime(ZonedDateTime modifiedEndTime) {
		this.modifiedEndTime = modifiedEndTime;
	}
//
	public ZonedDateTime getModifiedStartTime() {
		return modifiedStartTime;
	}
//
	public void setModifiedStartTime(ZonedDateTime modifiedStartTime) {
		this.modifiedStartTime = modifiedStartTime;
	}

	public ZonedDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(ZonedDateTime startTime) {
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

	@Override
	public String toString() {
		return "Worktime [id=" + id + ", comment=" + comment + ", endTime=" + endTime + ", modifiedBy=" + modifiedBy
				+ ", modifiedEndTime=" + modifiedEndTime + ", modifiedStartTime=" + modifiedStartTime + ", startTime="
				+ startTime + ", type=" + type + ", workdayId=" + workdayId + "]";
	}
	
	/*
	public Workday getWorkday() {
		return this.workday;
	}

	public void setWorkday(Workday workday) {
		this.workday = workday;
	}*/

    public Workday getWorkday() {
        return workday;
    }

    public void setWorkday(Workday workday) {
        this.workday = workday;
    }

	
}