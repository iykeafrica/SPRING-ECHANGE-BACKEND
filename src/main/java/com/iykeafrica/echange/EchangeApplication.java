package com.iykeafrica.echange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchangeApplication.class, args);
    }

    @Bean //it is annotated @Bean so that we can autowired it.
    public BCryptPasswordEncoder bCryptPasswordEncoder() { //used for securing password->
        return new BCryptPasswordEncoder();
    }
}
