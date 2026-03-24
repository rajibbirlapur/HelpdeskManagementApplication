package com.terracis.helpdeskmanagementapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.terracis.helpdeskmanagementapp.entity.User;
import com.terracis.helpdeskmanagementapp.exception.ResourceNotFoundException;
import com.terracis.helpdeskmanagementapp.repo.UserRepo;

@Service
public class UserService 
{
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String registerUser(User user)
	{
		if(userRepo.findByUsername(user.getUsername()) != null)
		{
			return "Username already exists";
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("USER");
		
		userRepo.save(user);
		
		return "Success";
	}

	public String resetPassword(String username, String newPassword) 
	{
		User user = userRepo.findByUsername(username);
		
		if(user == null)
			return "User not found";
		
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepo.save(user);
		
		return "Password updated";
	}

	public List<User> getAllUsers() 
	{
		return userRepo.findAll();
	}
	
	public User getUserById(Long id)
	{
		return userRepo.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
	}
	
	public void saveUserByAdmin(User user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
	}
	
	public boolean deleteUser(Long id, String loggedInUsername)
	{
		User user = userRepo.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
		
		// prevent self delete
		if(user.getUsername().equals(loggedInUsername))
			return false;
		
		// prevent deleting another ADMIN
		if (user.getRole().equals("ADMIN"))
			return false;
		
		userRepo.deleteById(id);
		return true;
	}
	
	public Page<User> getFilteredUsers(String keyword, String role, Pageable pageable)
	{
		if((keyword == null || keyword.isEmpty()) && (role == null || role.isEmpty()))
		{
			return userRepo.findAll(pageable);
		}
		
		if(keyword != null && !keyword.isEmpty() && role != null && !role.isEmpty())
		{
			return userRepo.findByUsernameContainingIgnoreCaseAndRole(keyword, role, pageable);
		}
		if(keyword != null && !keyword.isEmpty())
		{
			return userRepo.findByUsernameContainingIgnoreCase(keyword, pageable);
		}
		return userRepo.findByRole(role, pageable);
	}
	
}
