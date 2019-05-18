package com.example.demo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "Students")
public class Student extends User {
	@NotNull
	private Date EntryMonth;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Track track;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Entry entry;
	
	@Autowired
	@OneToOne
	private ApprovedPreRequisits approvedprerequisits; 
	
	@Autowired
	@ManyToOne
	private Faculty advisor;
	
	@Autowired
	@ManyToMany(fetch = FetchType.EAGER, cascade = {
	        CascadeType.PERSIST,
	        CascadeType.MERGE,
	        
	})
	private Set<CourseClass> ClassesToAttend = new HashSet<>();
	
	
	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public Entry getEntry() {
		return entry;
	}

	public Set<CourseClass> getClassesToAttend() {
		return ClassesToAttend;
	}

	public ApprovedPreRequisits getApprovedprerequisits() {
		return approvedprerequisits;
	}

	public Date getEntryDate() {
		return EntryMonth;
	}

	public void setEntryDate(Date entrymonth) {
		EntryMonth = entrymonth;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public Faculty getAdvisor() {
		return advisor;
	}

	public void setAdvisor(Faculty advisor) {
		this.advisor = advisor;
	}

	public void setApprovedprerequisits(ApprovedPreRequisits approvedprerequisits) {
		this.approvedprerequisits = approvedprerequisits;
	}

	public void setClassesToAttend(Set<CourseClass> classesToAttend) {
		ClassesToAttend = classesToAttend;
	}
	public void addclass(CourseClass classs) {
		ClassesToAttend.add(classs);
	}
}
