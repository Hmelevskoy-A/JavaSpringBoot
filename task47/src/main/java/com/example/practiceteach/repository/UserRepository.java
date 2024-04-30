package com.example.practiceteach.repository;

import com.example.practiceteach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(Integer id);

    User findByUsername(String username);

    @Query(
            value = "SELECT * FROM USERS u WHERE u.department= ?1",
            nativeQuery = true)
    List<User> getUsrsByDepartment(String department);


}