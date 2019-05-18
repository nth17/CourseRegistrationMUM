package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class CourseClass{
	
	@Id
	private String CourseClassId;
	
	@NotNull
	private int availableseats; 
	
	@Autowired
	@ManyToOne
	private Faculty Professor;
	
	@Autowired
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "ClassesToAttend")
	@Cascade(CascadeType.DELETE)
	private Set<Student> Students = new HashSet<>();
	
	@Autowired
	@ManyToOne
	@Cascade(CascadeType.DELETE)
	private Course course;
	
	@Autowired
	@ManyToOne
	@Cascade(CascadeType.DELETE)
	private Block block;
	
	@Autowired
	@OneToOne
	@Cascade(CascadeType.DELETE)
	private WaitingList ClassWaitingList;

	
	public String getCourseClassId() {
		return CourseClassId;
	}

	public void setCourseClassId(String courseClassId) {
		CourseClassId = courseClassId;
	}

	public void setProfessor(Faculty professor) {
		Professor = professor;
	}

	public void setStudents(Set<Student> students) {
		Students = students;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public void setClassWaitingList(WaitingList classWaitingList) {
		ClassWaitingList = classWaitingList;
	}

	public Faculty getProfessor() {
		return Professor;
	}

	public Set<Student> getStudents() {

		return Students;
	}

	public Course getCourse() {
		return course;
	}

	public Block getBlock() {
		return block;
	}

	public WaitingList getClassWaitingList() {
		return ClassWaitingList;
	}
	
	
	public int getAvailableseats() {
		return availableseats;
	}
	
	public String getavailableseats() {
		return ""+availableseats;
	}

	public void setAvailableseats(int availableseats) {
		this.availableseats = availableseats;
	}

	@Override
	public String toString() {
		return "CourseClass [CourseClassId=" + CourseClassId + ", course=" + course + "]";
	}
	
	public void addsttudent(Student student) {
		Students.add(student);
	}
	
	
}
