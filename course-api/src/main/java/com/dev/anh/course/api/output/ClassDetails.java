package com.dev.anh.course.api.output;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.dev.anh.course.model.consts.ClassType;
import com.dev.anh.course.model.consts.CourseLevel;

public record ClassDetails(
		int id,
		int courseId,
		CourseLevel  Level,
		String courseName,
		LocalDate startDate,
		ClassType classType,
		String remark,
		List<Schedule> shedules,
		boolean deleted,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {
	
	
	
	
	
}
