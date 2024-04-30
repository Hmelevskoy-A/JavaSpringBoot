package com.example.practiceteach;

import com.example.practiceteach.model.User;
import com.example.practiceteach.service.UserDetailsServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PracticeTeachApplication {


    public static void main(String[] args) {

        SpringApplication.run(PracticeTeachApplication.class, args);

    }

}
