package com.tricast;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.transaction.Transactional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tricast.repositories.OffDayRepository;
import com.tricast.repositories.OffDayLimitsRepository;
import com.tricast.repositories.SpecialdayRepository;
import com.tricast.repositories.UserRepository;
import com.tricast.repositories.WorkdayRepository;
import com.tricast.repositories.WorktimeRepository;
import com.tricast.repositories.entities.enums.WorktimeType;

@SpringBootApplication
public class CommandLineTestRunner implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(CommandLineTestRunner.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SpecialdayRepository specialdayRepository;
    
    @Autowired
    private WorkdayRepository workdayRepository;
    
    @Autowired
    private WorktimeRepository worktimeRepository;
    
    @Autowired
    private OffDayLimitsRepository offdayLimitsRepository;
    
    @Autowired
    private OffDayRepository offdayrepository;
    
    public static void main(String[] args) {
        SpringApplication.run(CommandLineTestRunner.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        LOG.info("Running...");
        // add your code here
        userRepository.findAll().forEach(user -> System.out.println(user.getGender()));
        specialdayRepository.findAll().forEach(specialday -> System.out.println(specialday.getDate()));
        workdayRepository.findAll().forEach(workday -> System.out.println(workday.getDate()));
        worktimeRepository.findAll().forEach(worktime -> System.out.println(worktime.getComment()));
        worktimeRepository.findByType(WorktimeType.DELEGACY).forEach(worktime -> System.out.println(worktime.getType()));
        offdayLimitsRepository.findAll().forEach(offdaylimits -> System.out.println(offdaylimits.getMaximumAmount()));
        offdayrepository.findAll().forEach(offday -> System.out.println(offday.getStatus()));
        ZonedDateTime s = ZonedDateTime.of(2020, 1, 1, 23, 59, 59, 999, ZoneId.systemDefault());
        ZonedDateTime ss = ZonedDateTime.of(2020, 1, 2, 23, 59, 59, 999, ZoneId.systemDefault());
    }
}
