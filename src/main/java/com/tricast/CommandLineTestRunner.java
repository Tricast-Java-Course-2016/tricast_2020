package com.tricast;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tricast.repositories.OffDayLimitRepository;
import com.tricast.repositories.OffDayRepository;
import com.tricast.repositories.UserRepository;

@SpringBootApplication
public class CommandLineTestRunner implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(CommandLineTestRunner.class);

    @Autowired
    private UserRepository userRepository;
    
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
        offdayrepository.findAll().forEach(offday -> System.out.println(offday.getStatus()));
    }
}
