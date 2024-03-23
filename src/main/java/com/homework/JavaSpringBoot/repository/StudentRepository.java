package com.homework.JavaSpringBoot.repository;


import com.homework.JavaSpringBoot.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByFirstName(String username);

}
