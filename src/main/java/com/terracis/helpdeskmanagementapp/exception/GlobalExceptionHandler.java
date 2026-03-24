package com.terracis.helpdeskmanagementapp.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler 
{
	@ExceptionHandler
	public String handleResourceNotFound(ResourceNotFoundException ex, Model model)
	{
		model.addAttribute("errorMessage", ex.getMessage());
		
		return "error-page";
	}
	
	@ExceptionHandler
	public String handleGenericException(Exception ex, Model model)
	{
		model.addAttribute("errorMessage", "Something went wrong: " + ex.getMessage());
		
		return "error-page";
	}

}
