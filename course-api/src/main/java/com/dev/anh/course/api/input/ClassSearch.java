package com.dev.anh.course.api.input;

import com.dev.anh.course.model.consts.ClassType;
import com.dev.anh.course.model.consts.CourseLevel;

public record ClassSearch(
	CourseLevel level,
	ClassType type,
	Boolean deleted,
	String keyword) {

}
