package com.dev.anh.course.api.output;

import java.time.LocalDateTime;
import java.util.List;

import com.dev.anh.course.model.consts.CourseLevel;
import com.dev.anh.course.model.entity.Course;

public record CourseDetails(
		int id,
		String name,
		CourseLevel  Level,
		String descirption,
		List<ClassListItem> classes, 
		boolean deleted,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {

	public static CourseDetails from(Course entity) {
		var classes = entity.getClasses().stream().map(ClassListItem::from).toList();
		
		return new CourseDetails(
					entity.getId(), 
					entity.getName(),
					entity.getLevel(), 
					entity.getDescription(), 
					classes, 
					entity.isDeleted() ,
					entity.getCreatedAt(),
					entity.getUpdatedAt());
	}	
}
