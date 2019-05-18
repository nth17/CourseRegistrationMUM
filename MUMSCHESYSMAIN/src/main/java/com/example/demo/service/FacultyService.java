package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Faculty;

public interface FacultyService {
	
	
	
	public List<Faculty> getAllFaculty();
	public Faculty saveFaculty(Faculty faculty);
	public Faculty getFacultyById(String userId);
	public Faculty getFacultyByFirstName(String firstName);
	
	
	

}
