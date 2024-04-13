package com.homework.JavaSpringBoot.controller;


import com.homework.JavaSpringBoot.dto.PostDTO;
import com.homework.JavaSpringBoot.dto.UserDTO;

import com.homework.JavaSpringBoot.model.Post;
import com.homework.JavaSpringBoot.model.Student;
import com.homework.JavaSpringBoot.model.StudyGroup;
import com.homework.JavaSpringBoot.model.User;
import com.homework.JavaSpringBoot.repository.PostViewRepository;
import com.homework.JavaSpringBoot.repository.StudentRepository;
import com.homework.JavaSpringBoot.repository.UserRepository;

import com.homework.JavaSpringBoot.service.StudentService;
import com.homework.JavaSpringBoot.service.StudyGroupService;
import com.homework.JavaSpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	StudentService studentService;
		@Autowired
	UserService userService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	StudyGroupService groupService;


@GetMapping("/signup")
public String showSignUpForm(Student student) {
	return "add-student";
}



	@PostMapping("/addstudent")
	public String addStudent( Student student, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-student";
		}

		studentRepository.save(student);
		return "redirect:/index";
	}


	@GetMapping({"/","/index"})
	public String showUserList(Model model) {
		model.addAttribute("students", studentRepository.findAll());
		return "index";
	}
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

		model.addAttribute("student", student);
		return "update-student";
	}
	@PostMapping("/update/{id}")
	public String updateStudent(@PathVariable("id") long id,  Student student,
							 BindingResult result, Model model) {
		if (result.hasErrors()) {
			student.setId(id);
			return "update-student";
		}

		studentRepository.save(student);
		return "redirect:/index";
	}

	@GetMapping("/delete/{id}")
	public String deleteStudent(@PathVariable("id") long id, Model model) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
		studentRepository.delete(student);
		return "redirect:/index";
	}



	//Task 46:
 @GetMapping("/groups")
 public ResponseEntity<List<StudyGroup>> groupsList() {
	return ResponseEntity.ok(groupService.listAllGroups());
}
	@GetMapping("/studentslistbygroupname/{name}")
	public ResponseEntity<List<Student>> studentsListByGroupName(@PathVariable("name") String name) {
		return ResponseEntity.ok(groupService.getStudentsFromGroupByName(name));
	}


	@GetMapping("/studentswithoutpayment")
	public ResponseEntity<List<Student>> studentsListWithoutPayment() {
		return ResponseEntity.ok(studentService.getStudentsWithoutPayment());
	}

	@GetMapping("/studentbyfirstname/{firstname}")
	public ResponseEntity<Student> studentByFirstName(@PathVariable("firstname") String firstname) {
		return ResponseEntity.ok(studentService.getStudentByFirstName(firstname));
	}
	@GetMapping("/studentbylastname/{lastname}")
	public ResponseEntity<Student> studentByLastName(@PathVariable("lastname") String lastname) {
		return ResponseEntity.ok(studentService.getStudentByLastName(lastname));
	}
	///
	@GetMapping("/studentsbygroupid/{groupid}")
	public ResponseEntity<List<Student>> studentsListByGroupId(@PathVariable("groupid") Long groupId) {
		return ResponseEntity.ok(studentService.getStudentsByGroupId(groupId));
	}
	@PatchMapping ("/changestudentproup/{id}/{groupid}")
	public ResponseEntity<Student>  changeGroup(@PathVariable("id") Long id, @PathVariable("groupid") Long groupId) {
		return ResponseEntity.ok(studentService.changeGroup(groupId, id));
	}
}
