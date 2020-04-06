package com.tricast.api.responses;

import java.time.ZonedDateTime;

import com.tricast.repositories.entities.enums.WorktimeType;

public class WorktimeGetResponse {
	private static final long serialVersionUID = 1L;
	
	private long id;

	private String comment;

	private ZonedDateTime endTime;

	private Integer modifiedBy;
	
	private ZonedDateTime startTime;
	
	private long workdayId;
	
	private WorktimeType type;

	private ZonedDateTime modifiedEndTime;

	private ZonedDateTime modifiedStartTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

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

	public long getWorkdayId() {
		return workdayId;
	}

	public void setWorkdayId(long workdayId) {
		this.workdayId = workdayId;
	}

	public ZonedDateTime getModifiedEndTime() {
		return modifiedEndTime;
	}

	public void setModifiedEndTime(ZonedDateTime modifiedEndTime) {
		this.modifiedEndTime = modifiedEndTime;
	}

	public ZonedDateTime getModifiedStartTime() {
		return modifiedStartTime;
	}

	public void setModifiedStartTime(ZonedDateTime modifiedStartTime) {
		this.modifiedStartTime = modifiedStartTime;
	}

	public static long getSerialversionworktimeid() {
		return serialVersionUID;
	}

	public ZonedDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(ZonedDateTime startTime) {
		this.startTime = startTime;
	}

	public WorktimeType getType() {
		return type;
	}

	public void setType(WorktimeType type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
