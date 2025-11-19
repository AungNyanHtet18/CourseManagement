package com.dev.anh.course.api.input;

import java.time.LocalDateTime;

import com.dev.anh.course.model.consts.CourseLevel;
import com.dev.anh.course.model.entity.Course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseForm(
	@NotBlank(message = "Please enter course name")
	String name,
	@NotNull(message = "Please select course level")
	CourseLevel level,
	@NotBlank(message = "Please enter course description")
	String description) {

	public Course entity() {
		var entity = new Course();
		entity.setName(name);
		entity.setLevel(level);
		entity.setDescription(description);
		entity.setCreatedAt(LocalDateTime.now());
		
		return entity;
	}

}
