package com.tricast.api.requests;

import java.time.ZonedDateTime;

import com.tricast.repositories.entities.enums.WorktimeType;

public class WorkTimeUpdateRequest {
	@Override
	public String toString() {
		return "WorkTimeUpdateRequest [id=" + id + ", comment=" + comment + ", endTime=" + endTime + ", modifiedBy="
				+ modifiedBy + ", startTime=" + startTime + ", type=" + type + ", workdayId=" + workdayId + "]"+"|||||";
	}

	private static final long serialVersionWorktimeID = 1L;
	
	private long id;

	private String comment;

	private ZonedDateTime endTime;

	private Integer modifiedBy;

	private ZonedDateTime startTime;

	private WorktimeType type;
	
	private long workdayId;

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

	public long getWorkdayId() {
		return workdayId;
	}

	public void setWorkdayId(long workdayId) {
		this.workdayId = workdayId;
	}

	public static long getSerialversionworktimeid() {
		return serialVersionWorktimeID;
	}
	
	
}
