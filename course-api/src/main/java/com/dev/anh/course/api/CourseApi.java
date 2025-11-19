package com.dev.anh.course.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dev.anh.course.api.input.CourseForm;
import com.dev.anh.course.api.input.CourseSearch;
import com.dev.anh.course.api.output.CourseDetails;
import com.dev.anh.course.api.output.CourseListItem;
import com.dev.anh.course.api.output.ModificationResult;
import com.dev.anh.course.model.service.CourseService;

@RestController
@RequestMapping("courses")
public class CourseApi {
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping
	List<CourseListItem>search(CourseSearch search) {
		 
		return courseService.search(search);
	}
	
	@GetMapping("{id}")
	CourseDetails findById(@PathVariable int id) {
		 return courseService.findById(id);
	}
	
	@PostMapping
	ModificationResult<Integer> create(@RequestBody @Validated CourseForm form) {
		
		 return courseService.create(form);
	}
	
	@PutMapping("{id}")
	ModificationResult<Integer> update(@PathVariable int id, 
									  @RequestBody @Validated CourseForm form) {
		
		 return courseService.update(id, form);
	}
	
}
