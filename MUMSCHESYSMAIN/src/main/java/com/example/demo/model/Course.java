package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
@Entity
public class Course {
	@Id
	private String CourseCode;
	
	@NotBlank
	private String CourseName;
	private String CourseDescription;
	private int CourseCredit;

	@Autowired
	@OneToMany (fetch = FetchType.EAGER)
	@Cascade(CascadeType.DELETE)
	private Set<CourseClass> CourseClasses = new HashSet<>();
		
	public void addcourseclass(CourseClass courseclass) {
		CourseClasses.add(courseclass);
	}
	public Set<CourseClass> getCourseClasses() {
		return CourseClasses;
	}

	public String getCourseCode() {
		return CourseCode;
	}

	public void setCourseCode(String courseCode) {
		CourseCode = courseCode;
	}

	public String getCourseName() {
		return CourseName;
	}

	public void setCourseName(String courseName) {
		CourseName = courseName;
	}

	public String getCourseDescription() {
		return CourseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		CourseDescription = courseDescription;
	}

	public int getCourseCredit() {
		return CourseCredit;
	}

	public void setCourseCredit(int courseCredit) {
		CourseCredit = courseCredit;
	}
		
}
