package com.example.practiceteach.service;

import com.example.practiceteach.DTO.DepartmentsManagersAndTasksDTO;
import com.example.practiceteach.DTO.ManagerWithDepDTO;
import com.example.practiceteach.DTO.UserDTO;
import com.example.practiceteach.DTO.UserTasksDTO;
import com.example.practiceteach.model.User;
import com.example.practiceteach.repository.TaskRepository;
import com.example.practiceteach.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }
        String pass = generatePassword();
        System.out.println("Пароль:" + pass);
        //  user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        user.setPassword(passwordEncoder.encode(pass));
        userRepository.save(user);
        return true;
    }

    public List<UserDTO> getUsrsAndTAskCountByDepartment(Integer monthNubmer, String department) {
        List<UserDTO> userDTOS = new ArrayList<>();
        List<User> usersByDepartment = userRepository.getUsrsByDepartment(department);
        for (int i = 0; i < usersByDepartment.size(); i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setName(usersByDepartment.get(i).getName());
            userDTO.setId(usersByDepartment.get(i).getId());
            userDTO.setDepartment(usersByDepartment.get(i).getDepartment());
            userDTO.setTasksDoneAtMonthCount(taskRepository.tasksByMonth(monthNubmer, usersByDepartment.get(i).getId()).size());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public List<DepartmentsManagersAndTasksDTO> getManagersAndTaskCountByDepartments(Integer monthNubmer) {
        List<DepartmentsManagersAndTasksDTO> departmentsManagersAndTasksDTOList = new ArrayList<>();
        List<User> usersFromDB = userRepository.findAll();
        List<String> departmentsNames = usersFromDB.stream().map(d -> d.getDepartment()).distinct()
                .collect(Collectors.toList());
        for (int i = 0; i < departmentsNames.size(); i++) {
            DepartmentsManagersAndTasksDTO depManagers = new DepartmentsManagersAndTasksDTO();
            depManagers.setDepartment(departmentsNames.get(i));
            List<UserDTO> userDTOS = getUsrsAndTAskCountByDepartment(monthNubmer, departmentsNames.get(i));
            depManagers.setTasksDoneAtMonthByDepartmentCount(userDTOS.stream().mapToInt(d -> d.getTasksDoneAtMonthCount()).sum());

            depManagers.setManagersCount(userDTOS.stream().filter(u -> userRepository.findById(u.getId()).get().getRoles()
                    .iterator().next().getName().matches("Менеджер")).collect(Collectors.toList()).size());
            System.out.println(depManagers);
            departmentsManagersAndTasksDTOList.add(depManagers);
        }
        return departmentsManagersAndTasksDTOList;
    }

    public UserTasksDTO getUserTasks(Integer monthNubmer, User authUser, Long id) {
        UserTasksDTO userTasksDTO = new UserTasksDTO();
        User userFromDB = userRepository.findById(id).get();
        if (userFromDB.getDepartment().equals(authUser.getDepartment())
                || authUser.getRoles().iterator().next().getName().equals("Администрация")) {
            userTasksDTO.setId(userFromDB.getId());
            userTasksDTO.setName(userFromDB.getName());
            userTasksDTO.setDepartment(userFromDB.getDepartment());
            userTasksDTO.setTasks(taskRepository.tasksByMonth(monthNubmer, userFromDB.getId()));
        }
        return userTasksDTO;

    }

    public ManagerWithDepDTO getManagerAndDepInfo(Long id) {

        ManagerWithDepDTO managerWithDepDTO = new ManagerWithDepDTO();
        User userFromDB = userRepository.findById(id).get();

        if (userFromDB.getRoles().iterator().next().getName().equals("Менеджер")) {
            List<User> usersByDepartment = userRepository.getUsrsByDepartment(userFromDB.getDepartment());
            List<UserTasksDTO> usersWithTaskFromDepartment = new ArrayList<>();
            for (int i = 0; i < usersByDepartment.size(); i++) {
                UserTasksDTO userTasksDTO = new UserTasksDTO();
                userTasksDTO.setId(usersByDepartment.get(i).getId());
                userTasksDTO.setName(usersByDepartment.get(i).getName());
                userTasksDTO.setDepartment(usersByDepartment.get(i).getDepartment());
                userTasksDTO.setTasks(usersByDepartment.get(i).getTasks());
                usersWithTaskFromDepartment.add(userTasksDTO);
            }
            managerWithDepDTO.setDepartment(userFromDB.getDepartment());
            managerWithDepDTO.setName(userFromDB.getName());
            managerWithDepDTO.setId(userFromDB.getId());
            managerWithDepDTO.setDepartmentUsers(usersWithTaskFromDepartment);
        } else {
            System.out.println("Пользователь не является менеджером");

        }
        return managerWithDepDTO;

    }

    public String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        String pwd = RandomStringUtils.random(10, characters);
        return pwd;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("Entering in loadUserByUsername Method...");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        logger.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user);
    }
}