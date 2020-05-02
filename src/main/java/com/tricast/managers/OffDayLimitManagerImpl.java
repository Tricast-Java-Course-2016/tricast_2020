package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.requests.OffDayLimitCreationRequest;
import com.tricast.api.responses.OffDayLimitCreationResponse;
import com.tricast.repositories.OffDayLimitRepository;
import com.tricast.repositories.entities.OffDayLimit;

@Component
public class OffDayLimitManagerImpl implements OffDayLimitManager {

    private OffDayLimitRepository offdayLimitRepository;

    @Autowired
    public OffDayLimitManagerImpl(OffDayLimitRepository offdayLimitRepository) {
        this.offdayLimitRepository = offdayLimitRepository;
    }

    @Override
    public Optional<OffDayLimit> getById(long id) {
        return offdayLimitRepository.findById(id);
    }

    @Override
    public OffDayLimit createOffDayLimit(OffDayLimit offdayLimitRequest) {
        return offdayLimitRepository.save(offdayLimitRequest);
    }
    
    @Override
    public OffDayLimitCreationResponse createOffDayFromRequest(OffDayLimitCreationRequest offDayLimitCreationRequest) {
    	OffDayLimit newOffDayLimit = mapCreationRequestToOffDayLimit(offDayLimitCreationRequest);
    	OffDayLimit createdOffDayLimit = offdayLimitRepository.save(newOffDayLimit);
    	return mapOffDayLimitToCreationResponse(createdOffDayLimit);
    }

    @Override
    public OffDayLimit updateOffDayLimit(OffDayLimit offdayLimitRequest) {
        return offdayLimitRepository.save(offdayLimitRequest);
    }

    @Override
    public void deleteOffDayLimit(long id) {
        offdayLimitRepository.deleteById(id);
    }

    @Override
    public boolean existsOffDayLimit(long id) {
        return offdayLimitRepository.existsById(id);
    }

    @Override
    public List<OffDayLimit> getAllOffDayLimits() {
        return (List<OffDayLimit>) offdayLimitRepository.findAll();
    }
    
    private OffDayLimit mapCreationRequestToOffDayLimit(OffDayLimitCreationRequest offDayLimitCreationRequest) { 
    	OffDayLimit newOffDayLimit = new OffDayLimit();
    	newOffDayLimit.setMaximumAmount(offDayLimitCreationRequest.getMaximumAmount());
    	newOffDayLimit.setType(offDayLimitCreationRequest.getType());
    	newOffDayLimit.setUserId(offDayLimitCreationRequest.getUserId());
    	newOffDayLimit.setYear(offDayLimitCreationRequest.getYear());
    	return newOffDayLimit;
    }
    
    private OffDayLimitCreationResponse mapOffDayLimitToCreationResponse(OffDayLimit offDayLimit) { 
    	OffDayLimitCreationResponse createdOffDayLimit = new OffDayLimitCreationResponse();
    	createdOffDayLimit.setId(offDayLimit.getId());
    	createdOffDayLimit.setMaximumAmount(offDayLimit.getMaximumAmount());
    	createdOffDayLimit.setType(offDayLimit.getType());
    	createdOffDayLimit.setUserId(offDayLimit.getUserId());
    	createdOffDayLimit.setYear(offDayLimit.getYear());
    	return createdOffDayLimit;
    }
}
