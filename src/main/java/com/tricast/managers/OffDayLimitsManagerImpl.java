package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.tricast.repositories.OffdayLimitsRepository;
import com.tricast.repositories.entities.OffdayLimits;

public class OffDayLimitsManagerImpl implements OffDayLimitsManager {
	
	private OffdayLimitsRepository offdayLimitsRepository;
	
	@Autowired
	public OffDayLimitsManagerImpl(OffdayLimitsRepository offdayLimitsRepository) {
		this.offdayLimitsRepository = offdayLimitsRepository;
	}

	@Override
	public Optional<OffdayLimits> getById(long id) {
		return offdayLimitsRepository.findById(id);
	}

	@Override
	public OffdayLimits createOffdayLimits(OffdayLimits offdayLimitsRequest) {
		return offdayLimitsRepository.save(offdayLimitsRequest);
	}

	@Override
	public OffdayLimits updateOffdayLimits(OffdayLimits offdayLimitsRequest) {
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
	public List<OffdayLimits> getAllOffdayLimits() {
		return (List<OffdayLimits>) offdayLimitsRepository.findAll();
	}
}
