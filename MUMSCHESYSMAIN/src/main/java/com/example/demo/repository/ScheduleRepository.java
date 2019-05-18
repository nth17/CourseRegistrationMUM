package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Schedule;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, String>{

}
