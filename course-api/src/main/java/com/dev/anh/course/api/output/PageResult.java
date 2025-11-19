package com.dev.anh.course.api.output;

import java.util.List;

public record PageResult<T>(
		List<T>list,
		PageInfo pageInfo) {
		
}
