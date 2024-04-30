package com.example.practiceteach.DTO;

import com.example.practiceteach.model.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTasksDTO {
    private Long id;
    private String name;
    private String department;
    private List<Task> tasks;
}
