package com.example.practiceteach.controller;

import com.example.practiceteach.DTO.UserDTO;
import com.example.practiceteach.DTO.UserTasksDTO;
import com.example.practiceteach.model.Task;
import com.example.practiceteach.model.User;
import com.example.practiceteach.repository.TaskRepository;
import com.example.practiceteach.repository.UserRepository;
import com.example.practiceteach.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;


@RestController
@Tag(name = "Managers API")
public class ManagerController {



    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserRepository userRepository;


   /* Пользователь с ролью менеджера может получить информацию о каждом пользователе
    в его департаменте и сколько задач он выполнил за конкретный месяц*/



    @PreAuthorize("hasAuthority('МЕНЕДЖЕР')")
    @GetMapping("/users/{monthNumber}")

    public List<UserDTO> getDepartmentUsersInfo( @PathVariable("monthNumber") String monthNumber) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return userDetailsService.getUsrsAndTAskCountByDepartment
                    (Integer.valueOf(monthNumber),userRepository.findByUsername(auth.getName()).getDepartment());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

   /* а также отдельно информацию о конкретном пользователе и выполненных им задачах за месяц*/

    @PreAuthorize("hasAnyAuthority('МЕНЕДЖЕР','АДМИНИСТРАЦИЯ')")
    @GetMapping("/user")
    public UserTasksDTO getuserMonthTasks(@RequestParam Long userId,
                                          @RequestParam Integer monthNumber) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();


            return  userDetailsService.getUserTasks(monthNumber
                    , userRepository.findByUsername(auth.getName()),userId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
