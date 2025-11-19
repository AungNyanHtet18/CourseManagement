package com.dev.anh.course.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.anh.course.api.input.CourseForm;
import com.dev.anh.course.api.input.CourseSearch;
import com.dev.anh.course.api.output.CourseDetails;
import com.dev.anh.course.api.output.CourseListItem;
import com.dev.anh.course.api.output.ModificationResult;
import com.dev.anh.course.model.entity.Course;
import com.dev.anh.course.model.repo.CourseRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

@Service
@Transactional(readOnly = true)
public class CourseService {

	@Autowired
	private CourseRepo courseRepo;
	
	public List<CourseListItem> search(CourseSearch search) {
			
		Function<CriteriaBuilder, CriteriaQuery<CourseListItem>> queryFunc = cb-> {
			var cq = cb.createQuery(CourseListItem.class);
			var root = cq.from(Course.class);
			
			CourseListItem.select(cq,root);
			cq.where(search.where(cb,root));
			
			return cq;
		};
		
		return courseRepo.search(queryFunc);
	}

	public CourseDetails findById(int id) {
		return courseRepo.findById(id).map(CourseDetails::from).orElseThrow();
	}

	@Transactional
	public ModificationResult<Integer> create(CourseForm form) {
		var entity =  courseRepo.save(form.entity()); //it will return entity object that include id  after saving
	  return new ModificationResult<Integer>(entity.getId());
	}

	@Transactional
	public ModificationResult<Integer> update(int id, CourseForm form) {
		var entity = courseRepo.findById(id).orElseThrow(); // entity object is in managed state
		entity.setName(form.name());
		entity.setLevel(form.level());
		entity.setDescription(form.description());
		entity.setUpdatedAt(LocalDateTime.now());

		return new ModificationResult<Integer>(entity.getId());
	}
	
}
