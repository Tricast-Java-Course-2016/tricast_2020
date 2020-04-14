package com.tricast.api.responses;

import java.util.List;
import java.util.Map;

public class WorkTimeStatByIdResponse {
	
	private long userId;
	private int numberOfworkday;
	private int worktimehours;
	private int overtimes;
	private List<Integer> worktimesOfTheWeeks;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getNumberOfworkday() {
		return numberOfworkday;
	}
	public void setNumberOfworkday(int numberOfworkday) {
		this.numberOfworkday = numberOfworkday;
	}
	public int getWorktimehours() {
		return worktimehours;
	}
	public void setWorktimehours(int worktimehours) {
		this.worktimehours = worktimehours;
	}
	public int getOvertimes() {
		return overtimes;
	}
	public void setOvertimes(int overtimes) {
		this.overtimes = overtimes;
	}
	public List<Integer> getWorktimesOfTheWeeks() {
		return worktimesOfTheWeeks;
	}
	public void setWorktimesOfTheWeeks(List<Integer> worktimesOfTheWeeks) {
		this.worktimesOfTheWeeks = worktimesOfTheWeeks;
	}
	
}
