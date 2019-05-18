package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CourseClass;

@Repository
public interface CourseClassRepository extends CrudRepository<CourseClass, String> {

}
