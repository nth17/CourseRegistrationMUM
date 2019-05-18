package com.example.demo.model;

import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
@Entity
public class Block {
	@Id
	private String BlockId;
	
	@NotNull
	private Date StartDate;
	@NotNull
	private Date EndDate;
	
	
	@Autowired
	@OneToMany
	@Cascade(CascadeType.DELETE)
	private Set<CourseClass> Classes = new HashSet<>();
	
	public void addcourseclass(CourseClass courseclass) {
		Classes.add(courseclass);
	}
	@Cascade(CascadeType.DELETE)
	public Set<CourseClass> getClasses() {
		return Classes;
	}

	public void setClasses(Set<CourseClass> classes) {
		Classes = classes;
	}

	public void setBlockId(String blockId) {
		BlockId = blockId;
	}

	public Date getStartDate() {
		return StartDate;
	}

	@SuppressWarnings("deprecation")
	public String startdate() {
		DateFormatSymbols x =new DateFormatSymbols();
		return x.getMonths()[StartDate.getMonth() + 1];
	}
	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getEndDate() {
		return EndDate;
	}

	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}

	public String getBlockId() {
		return BlockId;
	}

	@Override
	public String toString() {
		return "Block [BlockId=" + BlockId + ", StartDate=" + StartDate + ", EndDate=" + EndDate + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((BlockId == null) ? 0 : BlockId.hashCode());
		result = prime * result + ((Classes == null) ? 0 : Classes.hashCode());
		result = prime * result + ((EndDate == null) ? 0 : EndDate.hashCode());
		result = prime * result + ((StartDate == null) ? 0 : StartDate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		if (BlockId == null) {
			if (other.BlockId != null)
				return false;
		} else if (!BlockId.equals(other.BlockId))
			return false;
		if (Classes == null) {
			if (other.Classes != null)
				return false;
		} else if (!Classes.equals(other.Classes))
			return false;
		if (EndDate == null) {
			if (other.EndDate != null)
				return false;
		} else if (!EndDate.equals(other.EndDate))
			return false;
		if (StartDate == null) {
			if (other.StartDate != null)
				return false;
		} else if (!StartDate.equals(other.StartDate))
			return false;
		return true;
	}
	
}
