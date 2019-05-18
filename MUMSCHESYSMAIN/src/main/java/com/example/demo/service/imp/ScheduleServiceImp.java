package com.example.demo.service.imp;
//package com.example.demo.service.imp;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.example.demo.model.Block;
//import com.example.demo.model.Schedule;
//import com.example.demo.repository.BlockDAO;
//import com.example.demo.repository.ScheduleDAO;
//import com.example.demo.service.ScheduleService;
//
//public class ScheduleServiceImp implements ScheduleService {
//
//	
//	@Autowired
//	private ScheduleDAO scheduleDAO;
//	
//	@Autowired
//	private BlockDAO blockDAO; 
//	
//	@Override
//	public Schedule saveSchedule(Schedule schedule) {
//		// TODO Auto-generated method stub
//		return scheduleDAO.save(schedule);
//	}
//
//	@Override
//	public Schedule findScheduleByScheduleId(String scheduleId) {
//		// TODO Auto-generated method stub
//		return scheduleDAO.findScheduleByScheduleID(scheduleId);
//	}
//
//	@Override
//	public List<Block> getAllBlocksintheSchedule() {
//		// TODO Auto-generated method stub
//		return blockDAO.getAllBlock();
//	}
//	
//	
//	
//
//}
