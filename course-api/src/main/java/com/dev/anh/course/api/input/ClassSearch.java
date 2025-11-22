package com.dev.anh.course.api.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.dev.anh.course.model.consts.ClassType;
import com.dev.anh.course.model.consts.CourseLevel;
import com.dev.anh.course.model.entity.Classes;
import com.dev.anh.course.model.entity.Classes_;
import com.dev.anh.course.model.entity.Course;
import com.dev.anh.course.model.entity.Course_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record ClassSearch(
	CourseLevel level,
	ClassType type,
	Boolean deleted,
	String keyword) {

	public Predicate[]  where(CriteriaBuilder cb, Root<Classes> root, Join<Classes, Course> course) {
		var params = new ArrayList<Predicate>();
		
		if(null != level) {
			params.add(cb.equal(course.get(Course_.level), level));
		}
		
		if(null != type) {
			 params.add(cb.equal(root.get(Classes_.type), type));
		}
		
		if(null != deleted) {
			 params.add(cb.equal(root.get(Classes_.deleted), deleted));
		}
		
		if(StringUtils.hasLength(keyword)) {
			 params.add(cb.or(
					cb.like(cb.lower(root.get(Classes_.remark)), "%%%s%%".formatted(keyword.toLowerCase())),  //the result is %keyword%
					cb.like(cb.lower(course.get(Course_.name)), "%%%s%%".formatted(keyword.toLowerCase())),
					cb.like(cb.lower(course.get(Course_.description)), "%%%s%%".formatted(keyword.toLowerCase()))
				   ));
		}
		
		return params.toArray(size -> new Predicate[size]);
	}

}
