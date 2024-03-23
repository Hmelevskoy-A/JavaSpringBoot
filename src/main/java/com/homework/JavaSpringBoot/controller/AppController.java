package com.homework.JavaSpringBoot.controller;


import com.homework.JavaSpringBoot.model.Student;
import com.homework.JavaSpringBoot.repository.StudentRepository;
import com.homework.JavaSpringBoot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	StudentService studentService;

	@Autowired
	MessageSource messageSource;
	@Autowired
	StudentRepository studentRepository;


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


}
