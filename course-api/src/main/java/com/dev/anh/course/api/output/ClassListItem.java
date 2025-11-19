package com.dev.anh.course.api.output;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.dev.anh.course.model.consts.ClassType;
import com.dev.anh.course.model.consts.CourseLevel;
import com.dev.anh.course.model.entity.Classes;

public record ClassListItem(
		int id,
		int courseId,
		CourseLevel  Level,
		String courseName,
		LocalDate startDate,
		ClassType classType,
		boolean deleted,
		LocalDateTime createdAt) {

	public static ClassListItem from(Classes entity) {
		 return new ClassListItem(
				 entity.getId(), 
				 entity.getCourse().getId(),
				 entity.getCourse().getLevel(), 
				 entity.getCourse().getName(), 
				 entity.getStartDate(), 
				 entity.getType(), 
				 entity.isDeleted(), 
				 entity.getCreatedAt());
	}
	
	
}
