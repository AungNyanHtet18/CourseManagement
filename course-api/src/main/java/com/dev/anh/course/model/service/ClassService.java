package com.dev.anh.course.model.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dev.anh.course.api.input.ClassForm;
import com.dev.anh.course.api.input.ClassSearch;
import com.dev.anh.course.api.output.ClassDetails;
import com.dev.anh.course.api.output.ClassListItem;
import com.dev.anh.course.api.output.ModificationResult;
import com.dev.anh.course.api.output.PageResult;
import com.dev.anh.course.model.repo.ClassesRepo;
import com.dev.anh.course.model.repo.CourseRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ClassService {
	
	@Autowired
	private ClassesRepo classesRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private ObjectMapper objectMapper; 

	public PageResult<ClassListItem> search(ClassSearch search, int page, int size) {
		
		return null;
	}

	public ClassDetails findById(int id) {
		
		return classesRepo.findById(id).map(ClassDetails::from).orElseThrow();
	}

	@Transactional
	public ModificationResult<Integer> create(ClassForm form) {
	    
		try {
			
			//Find Course
			var course = courseRepo.findById(form.courseId()).orElseThrow();
			
		    //Convert Schedule List to JSON String	
			var schedules = objectMapper.writeValueAsString(form.shedules());
			
			
			//Create Class Entity
		    var entity  = form.entity();
		    entity.setCourse(course);
		    entity.setShedules(schedules);

		    entity = classesRepo.save(entity);
		    
		    return new ModificationResult<Integer>(entity.getId());
			
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional
	public ModificationResult<Integer> update(int id, ClassForm form) {
		try {
			var entity = classesRepo.findById(id).orElseThrow();
			
			//Find Course
			var course =  courseRepo.findById(form.courseId()).orElseThrow();
			entity.setCourse(course);
			
			//Convert Schedule List to JSON string
			var schedules = objectMapper.writeValueAsString(form.shedules());
			entity.setShedules(schedules);
			
			entity.setStartDate(form.starDate());
			entity.setType(form.classType());
			entity.setRemark(form.remark());
			
			entity.setUpdatedAt(LocalDateTime.now());
			
			return new ModificationResult<Integer>(entity.getId());
			
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	
	}
	
}
