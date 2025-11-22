package com.dev.anh.course.api.output;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import com.dev.anh.course.model.consts.ClassType;
import com.dev.anh.course.model.consts.CourseLevel;
import com.dev.anh.course.model.entity.Classes;

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

	public static ClassDetails from(Classes entity, Function<String,List<Schedule>> mapper) {
		return new ClassDetails(
				entity.getId(), 
				entity.getCourse().getId(), 
				entity.getCourse().getLevel(), 
				entity.getCourse().getName(), 
				entity.getStartDate(),
				entity.getType(), 
				entity.getRemark(), 
				mapper.apply(entity.getShedules()), 
				entity.isDeleted(),
				entity.getCreatedAt(), 
				entity.getUpdatedAt());
	}

}
