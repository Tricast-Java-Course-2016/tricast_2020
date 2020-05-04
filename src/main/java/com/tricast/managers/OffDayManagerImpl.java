package com.tricast.managers;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.requests.OffDayRequest;
import com.tricast.api.responses.OffDayResponse;
import com.tricast.api.responses.UserResponse;
import com.tricast.repositories.OffDayRepository;
import com.tricast.repositories.UserRepository;
import com.tricast.repositories.entities.OffDayLimit;
import com.tricast.repositories.entities.Offday;
import com.tricast.repositories.entities.User;
import com.tricast.repositories.entities.enums.OffDayStatus;

@Component
public class OffDayManagerImpl implements OffDayManager {

	private OffDayRepository offDayRepository;
	private UserRepository userRepository;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Autowired
	public OffDayManagerImpl(OffDayRepository offDayRepository, UserRepository userRepository) {
		this.offDayRepository = offDayRepository;
		this.userRepository = userRepository;
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
    public OffDayResponse createOffDayFromRequest(OffDayRequest offdayRequest) {
    	Offday newOffDay = mapOffDayCreationRequestToOffDay(offdayRequest);
    	Offday createdOffDay = offDayRepository.save(newOffDay);
    	return mapOffDayToOffDayResponse(createdOffDay);
    }

	@Override
	public List<Offday> getAlloffDays() {
		/*Optional<User> newUser = userRepository.findById(Long.valueOf(1));
		User foundUser = newUser.get();
		StringBuilder fullName = new StringBuilder();
		fullName.append(foundUser.getLastName() + " ");
		fullName.append(foundUser.getMiddleName() + " ");
		fullName.append(foundUser.getFirstName());
		System.out.println(fullName.toString());*/
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

	private Offday mapOffDayCreationRequestToOffDay(OffDayRequest offDayRequest) {
		Offday newOffday = new Offday();
		newOffday.setDate(ZonedDateTime.now());
		newOffday.setType(offDayRequest.getType());
		newOffday.setStatus(OffDayStatus.REQUESTED);
		newOffday.setApprovedby(null);
		newOffday.setUserId(offDayRequest.getuserId());
		newOffday.setStartTime(offDayRequest.getStartTime());
		newOffday.setEndTime(offDayRequest.getEndTime());
		
		return newOffday;
	}
	
	private OffDayResponse mapOffDayToOffDayResponse(Offday offDay) {
		OffDayResponse createdOffDay = new OffDayResponse();
		Optional<User> newUser = userRepository.findById(offDay.getUserId());
		StringBuilder fullName = new StringBuilder();
		User foundUser = newUser.get();
		
		fullName.append(foundUser.getLastName() + " ");
		fullName.append(foundUser.getMiddleName() + " ");
		fullName.append(foundUser.getFirstName());
		createdOffDay.setFullName(fullName.toString());
		createdOffDay.setType(offDay.getType());
		createdOffDay.setUserId(offDay.getUserId());
		createdOffDay.setStartTime(offDay.getStartTime());
		createdOffDay.setEndTime(offDay.getEndTime());
		createdOffDay.setActualDayCount(offDay.getStartTime().until(offDay.getEndTime(), ChronoUnit.DAYS));
		return createdOffDay;
	}
}
