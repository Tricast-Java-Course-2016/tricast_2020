package com.tricast;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandLineTestRunner implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(CommandLineTestRunner.class);

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
