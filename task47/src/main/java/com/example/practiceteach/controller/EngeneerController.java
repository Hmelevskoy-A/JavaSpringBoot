package com.example.practiceteach.controller;

import com.example.practiceteach.model.Task;
import com.example.practiceteach.model.User;
import com.example.practiceteach.repository.TaskRepository;
import com.example.practiceteach.repository.UserRepository;
import com.example.practiceteach.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Tag(name = "Engeneers API")
public class EngeneerController {


    @Autowired
    TaskService taskService;
    @Autowired
    UserRepository userRepository;


    @PreAuthorize("hasAuthority('ИНЖЕНЕР')")
    @GetMapping("/tasks/{monthNumber}")
    public List<Task> getMonthTasks( @PathVariable("monthNumber") String monthNumber) {
        try {
     Authentication auth = SecurityContextHolder.getContext().getAuthentication();


            return taskService.openTasksFromMonth(Integer.valueOf(monthNumber),
                    userRepository.findByUsername(auth.getName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
