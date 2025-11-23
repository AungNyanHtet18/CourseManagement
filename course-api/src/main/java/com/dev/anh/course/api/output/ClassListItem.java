package com.dev.anh.course.api.output;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.dev.anh.course.model.consts.ClassType;
import com.dev.anh.course.model.consts.CourseLevel;
import com.dev.anh.course.model.entity.Classes;
import com.dev.anh.course.model.entity.Classes_;
import com.dev.anh.course.model.entity.Course;
import com.dev.anh.course.model.entity.Course_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public record ClassListItem(
		int id,
		int courseId,
		CourseLevel  Level,
		String courseName,
		LocalDate startDate,
		ClassType classType,
		int months,
		boolean deleted,
		LocalDateTime createdAt) {

	public static void select(CriteriaQuery<ClassListItem> cq, Root<Classes> root, Join<Classes, Course> course) {
		
		cq.multiselect(
			 root.get(Classes_.id),
			 course.get(Course_.id),
			 course.get(Course_.level),
			 course.get(Course_.name),
			 root.get(Classes_.startDate),
			 root.get(Classes_.type),
			 root.get(Classes_.months),			 
			 root.get(Classes_.deleted),
			 root.get(Classes_.createdAt));
	}
	
	public static ClassListItem from(Classes entity) {
		 return new ClassListItem(
				 entity.getId(), 
				 entity.getCourse().getId(),
				 entity.getCourse().getLevel(), 
				 entity.getCourse().getName(), 
				 entity.getStartDate(), 
				 entity.getType(), 
				 entity.getMonths(),			 
				 entity.isDeleted(), 
				 entity.getCreatedAt());
	}	
}
