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
//import com.example.demo.service.BlockService;
//
//public class BlockServiceImp implements BlockService {
//
//	
//	@Autowired
//	private BlockDAO blockDAO;
//	@Autowired
//	private ScheduleDAO scheduleDAO;
//	
//	@Override
//	public void saveBlock(Block block, Schedule scheduleId) {
//		//Schedule currentSchedule = scheduleService.getE
//		
//	}
//
//	@Override
//	public Block getBlock(String blockId) {
//		// TODO Auto-generated method stub
//		return blockDAO.findBlockByBlockID(blockId);
//	}
//
//	@Override
//	public List<Block> getAllBlock() {
//		// TODO Auto-generated method stub
//		return blockDAO.getAllBlock();
//	}
//
//	@Override
//	public void deleteBlock(String blockId) {
//		// TODO Auto-generated method stub
//		blockDAO.deleteById(blockId);
//	}
//
//	@Override
//	public void updateBlock(Block block) {
//		// TODO Auto-generated method stub
//		blockDAO.save(block);
//		
//	}
//
//}
