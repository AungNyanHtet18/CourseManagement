package com.dev.anh.course.api.output;

import java.time.LocalDateTime;

import com.dev.anh.course.model.consts.CourseLevel;
import com.dev.anh.course.model.entity.Course;
import com.dev.anh.course.model.entity.Course_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record CourseListItem(
	int id,
	String name,
	CourseLevel  Level,
	String description,
	boolean deleted,
	LocalDateTime createdAt) {

	public static void select(CriteriaQuery<CourseListItem> cq, Root<Course> root) {
		cq.multiselect(
				root.get(Course_.id),
				root.get(Course_.name),
				root.get(Course_.level),
				root.get(Course_.description),
				root.get(Course_.deleted),
				root.get(Course_.createdAt));
	}
}
