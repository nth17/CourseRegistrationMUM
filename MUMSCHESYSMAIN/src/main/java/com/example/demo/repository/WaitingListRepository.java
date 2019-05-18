package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.WaitingList;

@Repository
public interface WaitingListRepository extends CrudRepository<WaitingList, String> {

}
