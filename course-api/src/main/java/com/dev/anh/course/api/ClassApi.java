package com.dev.anh.course.api;

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

@RestController
@RequestMapping("classes")
public class ClassApi {
	

	@GetMapping
	PageResult<ClassListItem> search(ClassSearch search,
					@RequestParam(required = false,defaultValue = "0") int page, 
					@RequestParam(required = false,defaultValue = "10") int size) {
		
		 return null;
	}
	
	@GetMapping("{id}")
	ClassDetails findById(@PathVariable int id) {
		return null;
	}
	
	@PostMapping
	ModificationResult<Integer> create(@RequestBody @Validated ClassForm form) {
		 return null;
	}
	
	@PutMapping("{id}")
	ModificationResult<Integer> update(@PathVariable int id, 
									  @RequestBody @Validated ClassForm form) {
		
		 return null;
	}
	
}
