package com.example.practiceteach.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentsManagersAndTasksDTO {
    private String department;
    private Integer managersCount;
    private Integer tasksDoneAtMonthByDepartmentCount;
}
