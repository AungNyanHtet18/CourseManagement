package com.dev.anh.course.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.course.api.input.ClassForm;
import com.dev.anh.course.api.input.ClassSearch;
import com.dev.anh.course.api.output.ClassDetails;
import com.dev.anh.course.api.output.ClassListItem;
import com.dev.anh.course.api.output.ModificationResult;
import com.dev.anh.course.api.output.PageResult;
import com.dev.anh.course.model.service.ClassService;

@RestController
@RequestMapping("classes")
public class ClassApi {
	
	@Autowired
	private ClassService service;

	@GetMapping
	PageResult<ClassListItem> search(ClassSearch search,
					@RequestParam(required = false,defaultValue = "0") int page, 
					@RequestParam(required = false,defaultValue = "10") int size) {
		
		 return service.search(search, page, size);
	}
	
	@GetMapping("{id}")
	ClassDetails findById(@PathVariable int id) {
		return service.findById(id);
	}
	
	@PostMapping
	ModificationResult<Integer> create(@RequestBody @Validated ClassForm form) {
		 return service.create(form);
	}
	
	@PutMapping("{id}")
	ModificationResult<Integer> update(@PathVariable int id, 
									  @RequestBody @Validated ClassForm form) {
		
		 return service.update(id, form);
	}
	
}
