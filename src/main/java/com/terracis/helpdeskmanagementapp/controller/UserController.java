package com.terracis.helpdeskmanagementapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.terracis.helpdeskmanagementapp.entity.User;
import com.terracis.helpdeskmanagementapp.service.UserService;

@Controller
@RequestMapping("/admin")
public class UserController 
{
	@Autowired 
	private UserService userService;

	// View all users
	@GetMapping("/users")
	public String getAllUsers(Model model,
			                  @RequestParam(defaultValue = "0") int page,
			                  @RequestParam(defaultValue = "5") int size,
			                  @RequestParam(required = false) String keyword,
			                  @RequestParam(required = false) String role)
	{
		Pageable pageable = PageRequest.of(page, size);
		
		Page<User> userPage = userService.getFilteredUsers(keyword, role, pageable);
		
		model.addAttribute("users", userPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", userPage.getTotalPages());
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("role", role);
		return "admin-users";
	}
	
	// Show user creation form
	@GetMapping("/users/create")
	public String showCreateForm(Model model)
	{
		model.addAttribute("user", new User());
		
		return "admin-user-form";
	}
	
	// Save user 
	@PostMapping("/users/save")
	public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes)
	{
	    boolean isUpdate = (user.getId() != null);

	    userService.saveUserByAdmin(user);

	    if(isUpdate)
	    {
	        redirectAttributes.addFlashAttribute("successMessage", "User updated successfully");
	    }
	    else
	    {
	        redirectAttributes.addFlashAttribute("successMessage", "User created successfully");
	    }

	    return "redirect:/admin/users";
	}
	
	// Edit user
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes)
	{
		model.addAttribute("user", userService.getUserById(id));
		return "admin-user-form";
	}
	
	// Delete user
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes)
	{
		String username = authentication.getName();
		
		boolean deleted = userService.deleteUser(id, username);
		
		if(deleted)
			redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully");
		else 
			redirectAttributes.addFlashAttribute("errorMessage", "You can not delete this user");
		
		return "redirect:/admin/users";
	}
}
