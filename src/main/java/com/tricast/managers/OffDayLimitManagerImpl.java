package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
