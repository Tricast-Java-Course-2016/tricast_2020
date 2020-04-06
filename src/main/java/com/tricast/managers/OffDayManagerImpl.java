package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.requests.OffDayRequest;
import com.tricast.api.responses.OffDayResponse;
import com.tricast.repositories.OffDayRepository;
import com.tricast.repositories.entities.Offday;
import com.tricast.repositories.entities.Worktime;

@Component
public class OffDayManagerImpl implements OffDayManager{
	
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
	public Offday updateOffday(Offday offDayRequest) {
		return offDayRepository.save(offDayRequest);
	}
	
	@Override
	public List<Offday> getAlloffDays() {
		return (List<Offday>) offDayRepository.findAll();
	}
	
	@Override
	public Optional<Offday> getById(long id) {
		return offDayRepository.findById(id);
	}
	
	@Override
	public void deleteOffday(long leaveId) {
		List<Offday> offdays = (List<Offday>)offDayRepository.findAll();
		for (Offday o : offdays) {
			if (o.getId() == leaveId) {
				offDayRepository.deleteById(o.getId());
			}
		offDayRepository.deleteById(leaveId);
	}
	}
}
