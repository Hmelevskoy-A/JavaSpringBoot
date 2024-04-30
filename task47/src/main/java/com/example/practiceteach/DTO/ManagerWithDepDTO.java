package com.example.practiceteach.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagerWithDepDTO {
    private Long id;
    private String name;
    private String department;
    private List<UserTasksDTO> departmentUsers;
}
