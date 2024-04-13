package com.homework.JavaSpringBoot.repository;


import com.homework.JavaSpringBoot.model.Student;
import com.homework.JavaSpringBoot.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findFirstByFirstName(String username);
    Student findFirstByLastName(String username);
    List<Student> getStudentsByCoursePaymentFalse();
    List<Student> getStudentsByStudyGroupIs(StudyGroup studyGroup);

}
