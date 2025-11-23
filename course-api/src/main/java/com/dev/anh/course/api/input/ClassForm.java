package com.dev.anh.course.api.input;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.dev.anh.course.api.output.Schedule;
import com.dev.anh.course.model.consts.ClassType;
import com.dev.anh.course.model.entity.Classes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ClassForm(
	 @NotNull(message = "Please select course.")
	 Integer courseId,
	 @NotNull(message = "Please enter start date.")
	 LocalDate startDate,
	 @NotNull(message = "Please enter class type.")
	 ClassType classType,
	 @NotNull(message = "Please enter duration in months.")
	 Integer months,
	 String remark,
	 @NotEmpty(message = "Please enter schedule.")
	 List<@Valid Schedule> schedules) {

	public Classes entity() {
		var entity = new Classes();
		entity.setStartDate(startDate);
		entity.setType(classType);
		entity.setMonths(months);
		entity.setRemark(remark);
		entity.setCreatedAt(LocalDateTime.now());
		return entity;
	}
}
