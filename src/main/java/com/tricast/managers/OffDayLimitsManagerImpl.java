package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.tricast.repositories.OffDayLimitsRepository;
import com.tricast.repositories.entities.OffDayLimits;

public class OffDayLimitsManagerImpl implements OffDayLimitsManager {
	
	private OffDayLimitsRepository offdayLimitsRepository;
	
	@Autowired
	public OffDayLimitsManagerImpl(OffDayLimitsRepository offdayLimitsRepository) {
		this.offdayLimitsRepository = offdayLimitsRepository;
	}

	@Override
	public Optional<OffDayLimits> getById(long id) {
		return offdayLimitsRepository.findById(id);
	}

	@Override
	public OffDayLimits createOffdayLimits(OffDayLimits offdayLimitsRequest) {
		return offdayLimitsRepository.save(offdayLimitsRequest);
	}

	@Override
	public OffDayLimits updateOffdayLimits(OffDayLimits offdayLimitsRequest) {
		return offdayLimitsRepository.save(offdayLimitsRequest);
	}

	@Override
	public void deleteOffdayLimits(long id) {
		offdayLimitsRepository.deleteById(id);
	}
	
	@Override
	public boolean existsOffdayLimits(long id) {
		return offdayLimitsRepository.existsById(id);
	}

	@Override
	public List<OffDayLimits> getAllOffdayLimits() {
		return (List<OffDayLimits>) offdayLimitsRepository.findAll();
	}
}
