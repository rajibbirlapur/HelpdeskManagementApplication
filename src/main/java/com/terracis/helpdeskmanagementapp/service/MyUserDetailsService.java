package com.terracis.helpdeskmanagementapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.terracis.helpdeskmanagementapp.entity.User;
import com.terracis.helpdeskmanagementapp.entity.UserPrinciple;
import com.terracis.helpdeskmanagementapp.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User user = userRepo.findByUsername(username);
		if(user == null)
		{
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
		return new UserPrinciple(user);
	}

}
