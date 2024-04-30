package com.example.practiceteach.controller;

import com.example.practiceteach.DTO.DepartmentsManagersAndTasksDTO;
import com.example.practiceteach.DTO.ManagerWithDepDTO;
import com.example.practiceteach.DTO.UserDTO;
import com.example.practiceteach.model.Task;
import com.example.practiceteach.model.User;
import com.example.practiceteach.repository.TaskRepository;
import com.example.practiceteach.repository.UserRepository;
import com.example.practiceteach.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Tag(name = "Admins API")
public class AdminController {


    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserRepository userRepository;



    @PreAuthorize("hasAuthority('АДМИНИСТРАЦИЯ')")
    @GetMapping("/departments/{monthNumber}")
    public List<DepartmentsManagersAndTasksDTO> departmentsManagersAndTasksDTOList(@PathVariable("monthNumber") String monthNumber) {
        try {
            return userDetailsService.getManagersAndTaskCountByDepartments(Integer.valueOf(monthNumber));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



   /* Информацию по конкретному менеджеру и его отделу:*/
   @PreAuthorize("hasAuthority('АДМИНИСТРАЦИЯ')")
  @GetMapping("/manageranddepinfo/{id}")
  public ManagerWithDepDTO getMaDepartmentUsersInfo(@PathVariable("id") Long id) {
      try {

          return userDetailsService.getManagerAndDepInfo(id);
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
  }
}
