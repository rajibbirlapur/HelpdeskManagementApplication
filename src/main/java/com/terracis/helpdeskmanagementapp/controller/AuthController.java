package com.terracis.helpdeskmanagementapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.terracis.helpdeskmanagementapp.entity.User;
import com.terracis.helpdeskmanagementapp.service.UserService;


@Controller
public class AuthController 
{
	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String signupPage(Model model)
	{
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String registerUser(@ModelAttribute User user, Model model)
	{
		String result = userService.registerUser(user);
		
		if(result.equals("Username already exists"))
		{
			model.addAttribute("error", result);
			
			return "signup";
		}
		return "redirect:/login?success";
	}
	
	@GetMapping("/login")
	public String loginPage()
	{
		return "login";
	}
	
	// show forgot password page
	@GetMapping("/forgot-password")
	public String forgotPasswordPage()
	{
		return "forgot-password";
	}
	
	// handle reset password
	@PostMapping("/forgot-password")
	public String resetPassword(@RequestParam String username,
			                    @RequestParam String password,
			                    Model model)
	{
		String result = userService.resetPassword(username, password);
		if(result.equals("User not found"))
		{
			model.addAttribute("errorMessage", result);
			return "forgot-password";
		}
		model.addAttribute("successMessage", "Password reset successfully, Please login...");
		return "forgot-password";
	}
	
}