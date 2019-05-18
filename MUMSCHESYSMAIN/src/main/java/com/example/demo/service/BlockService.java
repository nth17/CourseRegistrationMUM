package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Block;
import com.example.demo.model.Schedule;

public interface BlockService {
	
	public void saveBlock(Block block, Schedule scheduleId);
	public Block getBlock(String blockId);
	public List<Block> getAllBlock();
	public void deleteBlock(String blockId);
	public void updateBlock(Block block);
}
