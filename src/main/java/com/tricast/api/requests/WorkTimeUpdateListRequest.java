package com.tricast.api.requests;

import java.util.LinkedList;
import java.util.List;

import javax.management.loading.PrivateClassLoader;

public class WorkTimeUpdateListRequest {
	
	@Override
	public String toString() {
		return "WorkTimeUpdateListRequest [datasList=" + datasList + "]";
	}

	private List<WorkTimeUpdateRequest> datasList = new LinkedList<WorkTimeUpdateRequest>();

	public List<WorkTimeUpdateRequest> getDatasList() {
		return datasList;
	}

	public void setDatasList(List<WorkTimeUpdateRequest> datasList) {
		this.datasList = datasList;
	}
	
	
}
