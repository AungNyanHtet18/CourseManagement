package com.dev.anh.course.api.input;

import java.time.LocalDate;
import java.util.List;
import com.dev.anh.course.api.output.Schedule;
import com.dev.anh.course.model.consts.ClassType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ClassForm(
	 @NotNull(message = "Please select course.")
	 Integer courseId,
	 @NotNull(message = "Please enter start date.")
	 LocalDate starDate,
	 @NotNull(message = "Please enter class type.")
	 ClassType classType,
	 String remark,
	 @NotEmpty(message = "Please enter schedule")
	 List<@Valid Schedule> shedules) {

	
	
}
