package com.example.practiceteach.repository;

import com.example.practiceteach.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {



    @Query(
            value = "SELECT * FROM task_task u WHERE EXTRACT(MONTH FROM u.created_at)= ?1 " +
                    "&& u.discription_status='выполнено' " +
                    "&& u.user_id= ?2  ",
            nativeQuery = true)
    List<Task> tasksByMonth(Integer monthNumber, Long id);


}
