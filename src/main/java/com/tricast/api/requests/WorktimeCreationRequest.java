package com.tricast.api.requests;

import java.time.ZonedDateTime;

import com.tricast.repositories.entities.enums.WorktimeType;

public class WorktimeCreationRequest {

	private static final long serialVersionWorktimeID = 1L;

	private String comment;

	private ZonedDateTime endTime;

	private Integer modifiedBy;

	private ZonedDateTime startTime;

	private WorktimeType type;
	
	public static long getSerialversionworktimeid() {
		return serialVersionWorktimeID;
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

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
}
