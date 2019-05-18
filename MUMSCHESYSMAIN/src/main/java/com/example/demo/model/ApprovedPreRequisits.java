package com.example.demo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
@Entity
public class ApprovedPreRequisits implements Serializable{
	@Id
	private String ApprovedPreReqId;
	
	@Autowired
	@OneToOne
	@JoinColumn(name = "UserID")
	private Student Student;
	
	@Autowired
	@OneToMany
	@Id
	
	private Set<Course> course = new HashSet<>();

	public Student getStudent() {
		return Student;
	}

	public Set<Course> getCourse() {
		return course;
	}
}
