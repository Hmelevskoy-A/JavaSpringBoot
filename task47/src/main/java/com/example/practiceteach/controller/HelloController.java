package com.example.practiceteach.controller;

import com.example.practiceteach.DTO.AuthRequestDTO;
import com.example.practiceteach.DTO.JwtResponseDTO;
import com.example.practiceteach.model.Task;
import com.example.practiceteach.model.User;
import com.example.practiceteach.repository.TaskRepository;
import com.example.practiceteach.service.JwtService;
import com.example.practiceteach.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Tag(name = "Basic Api")
public class HelloController {
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/api/hello")
    public String hello(){
        return "Hello";
    }
    @PostMapping("/api/v1/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            System.out.println("Starting token generation");
            JwtResponseDTO jwtResponseDTO = JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()).trim()).build();
            System.out.println(jwtResponseDTO);
            return jwtResponseDTO;
        } else {
            System.out.println("atatatat");
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
    @PostMapping("/saveuser")
    public Boolean createUser(@RequestBody User user){
        System.out.println(user);
        return userDetailsService.saveUser(user);
    }


    @PreAuthorize("hasAuthority('АДМИНИСТРАЦИЯ')")
    @GetMapping("/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @PreAuthorize("hasAuthority('ИНЖЕНЕР')")
    @GetMapping("/taskss")
    public List<Task> tests() {
        try {
            return taskRepository.findAll();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
