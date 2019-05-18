package com.example.demo.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class WaitingList {
	@Id
	private String WaitingListID;
	
	@Autowired
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Student> students;
	
	public Set<Student> getStudents() {
		return students;
	}
}
