package com.tricast.repositories.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.time.ZonedDateTime;


/**
 * The persistent class for the offdays database table.
 * 
 */
@Entity
@Table(name="offdays")
@NamedQuery(name="Offday.findAll", query="SELECT o FROM Offday o")
public class Offday implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_OFFDAYS", sequenceName = "SEQ_OFFDAYS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OFFDAYS")
	private long id;

	@Column(name = "approvedby")
	private Integer approvedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date date;

	@Column(name = "endtime")
	private ZonedDateTime endTime;

	@Column(name = "starttime")
	private ZonedDateTime startTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private OffDayStatus status;

	@Column(name = "type")
	private String type;

	@Column(name = "userid")
	private Integer userId;

	public Offday() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getApprovedby() {
		return this.approvedBy;
	}

	public void setApprovedby(Integer approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ZonedDateTime getEndTime() {
		return this.endTime;
	}

	public void setEndTime(ZonedDateTime endTime) {
		this.endTime = endTime;
	}

	public ZonedDateTime getStartTime() {
		return this.startTime;
	}

	public void setStarTtime(ZonedDateTime startTime) {
		this.startTime = startTime;
	}

	public OffDayStatus getStatus() {
		return this.status;
	}

	public void setStatus(OffDayStatus status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Offday [id=" + id + ", approvedBy=" + approvedBy + ", date=" + date + ", endTime=" + endTime
				+ ", startTime=" + startTime + ", status=" + status + ", type=" + type + ", userId=" + userId + "]";
	}

}