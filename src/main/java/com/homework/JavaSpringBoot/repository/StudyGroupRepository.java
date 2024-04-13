package com.homework.JavaSpringBoot.repository;



import com.homework.JavaSpringBoot.model.Student;
import com.homework.JavaSpringBoot.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    StudyGroup findByName(String name);


}
