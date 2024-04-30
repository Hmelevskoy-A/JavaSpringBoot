package com.example.practiceteach.service;

import com.example.practiceteach.model.Task;
import com.example.practiceteach.model.User;
import com.example.practiceteach.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;


    public List<Task> openTasksFromMonth(Integer monthNubmer, User authUser) {
        return taskRepository.tasksByMonth(monthNubmer, authUser.getId());
    }
}
