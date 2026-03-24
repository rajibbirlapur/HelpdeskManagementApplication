package com.terracis.helpdeskmanagementapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.terracis.helpdeskmanagementapp.entity.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> 
{

}
