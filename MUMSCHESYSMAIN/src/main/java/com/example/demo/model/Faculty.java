package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Faculty extends User {	
	
	@Autowired
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Student> advisees;
	
	@Autowired
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Course> CoursesToTeach;
	
	@Autowired
	@OneToMany(fetch = FetchType.EAGER)
	private Set<CourseClass> ClassesToTeach = new HashSet<>();
	
	private String isAvailable;
	
	public String isAvailable() {
		return isAvailable;
	}
	public void setAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	public Set<Course> getCoursesToTeach() {
		return CoursesToTeach;
	}
	public Set<CourseClass> getClassesToTeach() {
		return ClassesToTeach;
	}
	public Set<Student> getAdvisees() {
		return advisees;
	}
	public String getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	public void setAdvisees(Set<Student> advisees) {
		this.advisees = advisees;
	}
	public void setCoursesToTeach(Set<Course> coursesToTeach) {
		CoursesToTeach = coursesToTeach;
	}
	public void setClassesToTeach(Set<CourseClass> classesToTeach) {
		ClassesToTeach = classesToTeach;
	}
	public void addAdvisee(Student student) {
		advisees.add(student);
	}
	public void addclass(CourseClass courseclass) {
		ClassesToTeach.add(courseclass);
	}
	public void addcourse(Course course) {
		CoursesToTeach.add(course);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ClassesToTeach == null) ? 0 : ClassesToTeach.hashCode());
		result = prime * result + ((CoursesToTeach == null) ? 0 : CoursesToTeach.hashCode());
		result = prime * result + ((advisees == null) ? 0 : advisees.hashCode());
		result = prime * result + ((isAvailable == null) ? 0 : isAvailable.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		if (ClassesToTeach == null) {
			if (other.ClassesToTeach != null)
				return false;
		} else if (!ClassesToTeach.equals(other.ClassesToTeach))
			return false;
		if (CoursesToTeach == null) {
			if (other.CoursesToTeach != null)
				return false;
		} else if (!CoursesToTeach.equals(other.CoursesToTeach))
			return false;
		if (advisees == null) {
			if (other.advisees != null)
				return false;
		} else if (!advisees.equals(other.advisees))
			return false;
		if (isAvailable == null) {
			if (other.isAvailable != null)
				return false;
		} else if (!isAvailable.equals(other.isAvailable))
			return false;
		return true;
	}
	
	
}
