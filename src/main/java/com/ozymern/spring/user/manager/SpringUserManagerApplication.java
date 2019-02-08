package com.ozymern.spring.user.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class SpringUserManagerApplication implements CommandLineRunner {
    @Autowired
    //private Encrypt encrypt;
    private BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringUserManagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.err.println(passwordEncoder.encode("admin"));


    }

}
