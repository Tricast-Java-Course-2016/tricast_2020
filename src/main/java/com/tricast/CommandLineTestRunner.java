package com.tricast;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.hibernate.internal.util.ZonedDateTimeComparator;
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
    }
}
