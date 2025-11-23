package com.dev.anh.course.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dev.anh.course.api.input.ClassForm;
import com.dev.anh.course.api.input.ClassSearch;
import com.dev.anh.course.api.output.ClassDetails;
import com.dev.anh.course.api.output.ClassListItem;
import com.dev.anh.course.api.output.ModificationResult;
import com.dev.anh.course.api.output.PageResult;
import com.dev.anh.course.api.output.Schedule;
import com.dev.anh.course.exceptions.BusinessException;
import com.dev.anh.course.model.entity.Classes;
import com.dev.anh.course.model.entity.Classes_;
import com.dev.anh.course.model.entity.Course;
import com.dev.anh.course.model.repo.ClassesRepo;
import com.dev.anh.course.model.repo.CourseRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

@Service
public class ClassService {
	
	@Autowired
	private ClassesRepo classesRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private ObjectMapper objectMapper; 

	public PageResult<ClassListItem> search(ClassSearch search, int page, int size) {
		
		return classesRepo.search(queryFunc(search), countFunc(search), page, size);
	}

	private Function<CriteriaBuilder,CriteriaQuery<ClassListItem>> queryFunc(ClassSearch search) {
		return cb-> {
			 var cq = cb.createQuery(ClassListItem.class);
			 var root = cq.from(Classes.class);
			 
			// var course = root.join(Classes_.course,JoinType.INNER);
			var course = applyJoins(root);
			 
			 ClassListItem.select(cq, root, course);
			 cq.where(search.where(cb,root, course));
			 
			 cq.orderBy(cb.desc(root.get(Classes_.id)));
			 
			 return cq;
		};
	}

	private Function<CriteriaBuilder,CriteriaQuery<Long>> countFunc(ClassSearch search) {
		return cb->{
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Classes.class);
			
			//var course = root.join(Classes_.course,JoinType.INNER);
			var course = applyJoins(root);
			
			cq.select(cb.count(root.get(Classes_.id)));
			cq.where(search.where(cb, root, course));
			
			return cq;
		};
	}
	
	
	private static Join<Classes,Course> applyJoins(Root<Classes> root) {
		return root.join(Classes_.course, JoinType.INNER);
	}

	public ClassDetails findById(int id) {
		return classesRepo.findById(id).map(a -> ClassDetails.from(a, this::covert))
				.orElseThrow(() -> new BusinessException("There is no class  with id %s".formatted(id)));
	}
	
	
	@Transactional
	public ModificationResult<Integer> create(ClassForm form) {
	    
		try {
			
			//Find Course
			var course = courseRepo.findById(form.courseId())
								.orElseThrow(() -> new BusinessException("There is no course with id %s".formatted(form.courseId())));
			
		    //Convert Schedule List to JSON String	
			var schedules = objectMapper.writeValueAsString(form.schedules());
			
			//Create Class Entity
		    var entity  = form.entity();
		    entity.setCourse(course);
		    entity.setSchedules(schedules);
		    
		    entity = classesRepo.save(entity);
		    
		    return new ModificationResult<Integer>(entity.getId());
			
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional
	public ModificationResult<Integer> update(int id, ClassForm form) {
		try {
			var entity = classesRepo.findById(id)
								.orElseThrow(() -> new BusinessException("There is no class with id %s".formatted(id)));
			
			//Find Course
			var course =  courseRepo.findById(form.courseId())
								.orElseThrow(() -> new BusinessException("There is no course with id %s".formatted(form.courseId())));
			
			entity.setCourse(course);
			
			//Convert Schedule List to JSON string
			var schedules = objectMapper.writeValueAsString(form.schedules());
			entity.setSchedules(schedules);
			
			entity.setStartDate(form.startDate());
			entity.setType(form.classType());
			entity.setMonths(form.months());
			entity.setRemark(form.remark());
			
			entity.setUpdatedAt(LocalDateTime.now());
			
			return new ModificationResult<Integer>(entity.getId());
			
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	
	private  List<Schedule> covert(String json) {
		 try {
			return objectMapper.readValue(json, new TypeReference <List<Schedule>> () {
				 
			});
		}  catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
