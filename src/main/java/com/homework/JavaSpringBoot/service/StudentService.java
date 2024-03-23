package com.homework.JavaSpringBoot.service;



import com.homework.JavaSpringBoot.model.Student;
import com.homework.JavaSpringBoot.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("studentService")
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;


	public Student getStudent(Long id) {
		return studentRepository.findById(id).get();
	}


	public Long saveStudent(Student st) {
		return studentRepository.save(st).getId();
	}


	public List<Student> listAllStudents() {
		return studentRepository.findAll();
	}


	public void update(Long id, Student st) {

		Student stEntity = studentRepository.findById(id).get();
		if (stEntity != null) {
			stEntity.setFirstName(st.getFirstName());
			stEntity.setLastName(st.getLastName());
			stEntity.setGrade(st.getGrade());
			studentRepository.save(stEntity);
		}
	}


	public void delete(Long id) {
		Student stEntity = studentRepository.findById(id).get();
		if (stEntity != null) {
			studentRepository.delete(stEntity);
		}
	}


	public boolean isStudentUnique(Long id) {
		Student student = studentRepository.findById(id).get();
		return (student == null || (id != null & !id.equals(student.getId())));
	}

}
