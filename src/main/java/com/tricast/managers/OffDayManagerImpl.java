package com.tricast.managers;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
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
	public List<OffDayResponse> getAllOffDays() {
		return offDayMapper(offDayRepository.findAll());
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
	
	@Override
	public List<OffDayResponse> getAllUnApprovedOffDays() {
		List<Offday> offdays = offDayRepository.findAll();
		List<OffDayResponse> unApprovedOffDays = new ArrayList<>();
		
		for (Offday offday : offdays)
			if (offday.getApprovedby().equals(1)) // null értékkel nullexeption
				unApprovedOffDays.add(mapOffDayToOffDayResponse(offday));
		
		return unApprovedOffDays;
	}
	
	@Override
	public List<OffDayResponse> getAllCurrentMonthOffDays() {
		List<Offday> offdays = offDayRepository.findAll();
		List<OffDayResponse> currentMonthOffDayList = new ArrayList<>();
		int currentMonth = ZonedDateTime.now().getMonthValue();
		 
		for (Offday offday : offdays)
			if (offday.getStartTime().getMonthValue() == currentMonth)
				currentMonthOffDayList.add(mapOffDayToOffDayResponse(offday));
		
		return currentMonthOffDayList;
	}

	private Offday mapOffDayCreationRequestToOffDay(OffDayRequest offDayRequest) {
		Offday newOffday = new Offday();
		newOffday.setDate(ZonedDateTime.now());
		newOffday.setType(offDayRequest.getType());
		newOffday.setStatus(OffDayStatus.REQUESTED);
		newOffday.setApprovedby(1); // null értékkel nullexception
		newOffday.setUserId(offDayRequest.getuserId());
		newOffday.setStartTime(ZonedDateTime.parse(offDayRequest.getStartTime()));
		newOffday.setEndTime(ZonedDateTime.parse(offDayRequest.getEndTime()));
		
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
	
	private List<OffDayResponse> offDayMapper(List<Offday> offdays) {
		List<OffDayResponse> offdayResponseList = new ArrayList<>();
		offdays.forEach(offday -> {
			offdayResponseList.add(mapOffDayToOffDayResponse(offday));
		});
		return offdayResponseList;
	}
}
