package com.homework.JavaSpringBoot.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;


@Entity
@Table(name = "students")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "grade", nullable = false)
	private Integer grade;

	@Column(name = "course_payment", nullable = false)
	private Boolean coursePayment;


	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "studyGroup_id")
	private StudyGroup studyGroup;
	public Student() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Boolean getCoursePayment() {
		return coursePayment;
	}

	public void setCoursePayment(Boolean coursePayment) {
		this.coursePayment = coursePayment;
	}

	public StudyGroup getStudyGroup() {
		return studyGroup;
	}

	public void setStudyGroup(StudyGroup studyGroup) {
		this.studyGroup = studyGroup;
	}

	public Student(Long id, String firstName, String lastName, Integer grade, Boolean coursePayment) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.grade = grade;
		this.coursePayment = coursePayment;
	}
}
