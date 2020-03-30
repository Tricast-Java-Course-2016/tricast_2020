package com.tricast.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.repositories.OffDayRepository;
import com.tricast.repositories.entities.Offday;

@Component
public class OffDayManagerImpl {
	
	private OffDayRepository offDayRepository;
	
	@Autowired
	public OffDayManagerImpl(OffDayRepository offDayRepository) {
		this.offDayRepository = offDayRepository;
	}
	
	@Override
	public Offday createOffday(Offday offDayRequest) {
		return offDayRepository.save(offDayRequest);
	}
	
	@Override
	public Offday modifyOffday(Offday modifyOffday) {
		return offDayRepository.save(modifyOffday);
	}
	
	@Override
	public void deleteOffday(long LeaveId) {
		offDayRepository.delete(LeaveId);
	}
	
	@Override
	public List<Offday> getAll() {
		return (List<Offday>) offDayRepository.findAll();
	}
	
	@Override
	public Offday getById(long id) {
		return offDayRepository.findById(id);
	}
}
