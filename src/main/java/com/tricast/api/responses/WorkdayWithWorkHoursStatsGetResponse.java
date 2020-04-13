package com.tricast.api.responses;

import java.util.List;

public class WorkdayWithWorkHoursStatsGetResponse {

	private List<WorkdayGetResponse> workdaysGetResponse;
	
	private int workhoursCurrentWeek;
	
	private int workhoursPreviouseWeek;
	
	private int workminutesCurrentWeek;
	
	private int workminutesPreviouseWeek;

	public int getWorkminutesCurrentWeek() {
		return workminutesCurrentWeek;
	}

	public void setWorkminutesCurrentWeek(int workminutesCurrentWeek) {
		this.workminutesCurrentWeek = workminutesCurrentWeek;
	}

	public int getWorkminutesPreviouseWeek() {
		return workminutesPreviouseWeek;
	}

	public void setWorkminutesPreviouseWeek(int workminutesPreviouseWeek) {
		this.workminutesPreviouseWeek = workminutesPreviouseWeek;
	}

	public List<WorkdayGetResponse> getWorkdaysGetResponse() {
		return workdaysGetResponse;
	}

	public void setWorkdaysGetResponse(List<WorkdayGetResponse> workdaysGetResponse) {
		this.workdaysGetResponse = workdaysGetResponse;
	}

	public int getWorkhoursCurrentWeek() {
		return workhoursCurrentWeek;
	}

	public void setWorkhoursCurrentWeek(int workhoursCurrentWeek) {
		this.workhoursCurrentWeek = workhoursCurrentWeek;
	}

	public int getWorkhoursPreviouseWeek() {
		return workhoursPreviouseWeek;
	}

	public void setWorkhoursPreviouseWeek(int workhoursPreviouseWeek) {
		this.workhoursPreviouseWeek = workhoursPreviouseWeek;
	}
	
	
}
