package com.dev.anh.course.model.consts;

public enum CourseLevel {
		Basic, 
		Intermediate, 
		Advance, 
		AllInOne{
			@Override
			public String getDisplayName() {
				return "All in One"; // CourseLevel.AllInOne.name();     // "AllInOne"
			} 
		};
	
	
	
	public String getDisplayName() {
		 return name(); //All constants share a default rule //(AllInOne) needs special behavior
		 				// CourseLevel.Basic.name();        // "Basic"
					    // CourseLevel.Intermediate.name(); // "Intermediate"
					    // CourseLevel.Advance.name();      // "Advance"
					    
	}
	
}
