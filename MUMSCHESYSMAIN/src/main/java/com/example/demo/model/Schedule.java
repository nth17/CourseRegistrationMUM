package com.example.demo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
@Entity
public class Schedule {
	@Id
	private String ScheduleId;
	
	@NotNull
	private Date StartDate;
	@NotNull
	private Date EndDate;
	
	@Autowired
	@OneToMany
	@Cascade(CascadeType.DELETE)
	private Set<Block> Blocks = new HashSet<>();

	public String getScheduleId() {
		return ScheduleId;
	}
	public void addblock(Block block) {
		Blocks.add(block);
	}
	public void setScheduleId(String scheduleId) {
		ScheduleId = scheduleId;
	}

	public Date getStartDate() {
		return StartDate;
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

	public Set<Block> getBlocks() {
		return Blocks;
	}
	public void setBlocks(Set<Block> blocks) {
		Blocks = blocks;
	}
	
	
	
}
