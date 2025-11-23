package com.dev.anh.course.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.dev.anh.course.model.BaseRespository;
import com.dev.anh.course.model.entity.Course;

public interface CourseRepo extends BaseRespository<Course, Integer>{
	
	Optional<Course> findOneByName(String name);
	
	@Query("select count(t) from Course t where name = :name")
	Long countByName(String name);
}
