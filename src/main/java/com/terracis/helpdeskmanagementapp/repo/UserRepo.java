package com.terracis.helpdeskmanagementapp.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.terracis.helpdeskmanagementapp.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>
{

	Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
	
	Page<User> findByRole(String role, Pageable pageable);
	
	Page<User> findByUsernameContainingIgnoreCaseAndRole(String username, String role, Pageable pageable);

	User findByUsername(String username);

}
