package com.dev.anh.course.model.consts;

public enum ClassType {
	Zoom("Online"), 
	Recorded("Online"), 
	Weekend("Campus"), 
	Weekday("Campus");
	
	private String value;

	private ClassType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getDisplayName() {
		 return "%s %s class".formatted(value, name());
	}
	
}
