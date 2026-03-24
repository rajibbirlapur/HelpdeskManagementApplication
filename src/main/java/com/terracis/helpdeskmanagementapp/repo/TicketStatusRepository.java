package com.terracis.helpdeskmanagementapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.terracis.helpdeskmanagementapp.entity.TicketStatus;

public interface TicketStatusRepository extends JpaRepository<TicketStatus, Long>
{

	TicketStatus findByNameIgnoreCase(String name);

}
