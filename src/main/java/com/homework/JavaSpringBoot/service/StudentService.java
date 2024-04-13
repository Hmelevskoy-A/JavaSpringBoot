package com.homework.JavaSpringBoot.service;



import com.homework.JavaSpringBoot.model.Student;
import com.homework.JavaSpringBoot.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("studentService")
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private StudyGroupService studyGroupService;


	public Student getStudent(Long id) {
		return studentRepository.findById(id).get();
	}





	public List<Student> listAllStudents() {
		return studentRepository.findAll();
	}








	public boolean isStudentUnique(Long id) {
		Student student = studentRepository.findById(id).get();
		return (student == null || (id != null & !id.equals(student.getId())));
	}

	//Task46:
	public Long saveStudent(Student st) {
		return studentRepository.save(st).getId();
	}
	public void update(Long id, Student st) {

		Student stEntity = studentRepository.findById(id).get();
		if (stEntity != null) {
			stEntity.setFirstName(st.getFirstName());
			stEntity.setLastName(st.getLastName());
			stEntity.setGrade(st.getGrade());
			stEntity.setCoursePayment(st.getCoursePayment());
			studentRepository.save(stEntity);
		}
	}
	public void delete(Long id) {
		Student stEntity = studentRepository.findById(id).get();
		if (stEntity != null) {
			studentRepository.delete(stEntity);
		}
	}
	public List<Student> getStudentsWithoutPayment() {
		return studentRepository.getStudentsByCoursePaymentFalse();
	}
	public Student getStudentByFirstName(String firstName) {
		return studentRepository.findFirstByFirstName(firstName);
	}
	public Student getStudentByLastName(String lastName) {
		return studentRepository.findFirstByLastName(lastName);
	}
	public List<Student> getStudentsByGroupId(Long groupId) {
		return studentRepository.getStudentsByStudyGroupIs(studyGroupService.getGroup(groupId));
	}
	public Student changeGroup(Long groupId, Long id) {
		Student stEntity = studentRepository.findById(id).get();
		stEntity.setStudyGroup(studyGroupService.getGroup(groupId));
		return studentRepository.save(stEntity);
	}
}
