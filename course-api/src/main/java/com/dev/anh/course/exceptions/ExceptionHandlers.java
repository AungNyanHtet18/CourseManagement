package com.dev.anh.course.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST) //bad request from client (400)
	List<String> handle(BusinessException e) {
		 return List.of(e.getMessage());
	}
	
	@ExceptionHandler
	@ResponseStatus(code =HttpStatus.BAD_REQUEST)
	List<String> handle(MethodArgumentNotValidException e) { //this handler can response when user requested form is empty
		 return e.getFieldErrors()
				 .stream()
				 .map(a -> a.getDefaultMessage())
				 .toList();
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	List<String> handle(Throwable e){
		  e.printStackTrace();
		 return List.of("Something go wrong");
	}
	
}
