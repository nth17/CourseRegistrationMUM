package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Block;
import com.example.demo.model.Schedule;

public interface ScheduleService {
	
	public Schedule saveSchedule(Schedule schedule);
	public Schedule findScheduleByScheduleId(String scheduleId);
	public List<Block> getAllBlocksintheSchedule();

}
