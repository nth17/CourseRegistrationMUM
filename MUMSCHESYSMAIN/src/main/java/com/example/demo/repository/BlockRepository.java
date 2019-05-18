package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Block;

@Repository
public interface BlockRepository extends CrudRepository<Block, String>{

}
