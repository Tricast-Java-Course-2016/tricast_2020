package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.requests.OffDayRequest;
import com.tricast.api.responses.OffDayResponse;
import com.tricast.repositories.OffDayRepository;
import com.tricast.repositories.entities.Offday;

@Component
public class OffDayManagerImpl implements OffDayManager {

	private OffDayRepository offDayRepository;

	@Autowired
	public OffDayManagerImpl(OffDayRepository offDayRepository) {
		this.offDayRepository = offDayRepository;
	}

    @Override
    public List<OffDayResponse> getAllOffDayByOffDayId(long offdayId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<OffDayResponse> getAllOffDayByOffDayId(int loggedInUser, long offdayId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public OffDayResponse createOffDayRequest(OffDayRequest offdayRequest) {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public List<Offday> getAlloffDays() {
		return offDayRepository.findAll();
	}

	@Override
	public Optional<Offday> getById(long id) {
		return offDayRepository.findById(id);
	}

	@Override
	public void deleteOffday(long leaveId) {
		List<Offday> offdays = offDayRepository.findAll();
		for (Offday o : offdays) {
			if (o.getId() == leaveId) {
				offDayRepository.deleteById(o.getId());
			}
		}
	}

	private Offday mapOffDayRequestToOffday(OffDayRequest offDayRequest) {
		Offday newOffday = new Offday();
		newOffday.setStarTtime(offDayRequest.getFromDay());
		newOffday.setEndTime(offDayRequest.getToDay());
		newOffday.setType(offDayRequest.getType());
		return newOffday;
	}

}
