package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Course;

public interface CourseService {
	
	public Course save(Course course);
	
	public List<Course> getAllCourses();
	
	public Course getCourseByName(String courseName);
	
	public Course getCourseByCourseCode(String courseCode);
	

}
