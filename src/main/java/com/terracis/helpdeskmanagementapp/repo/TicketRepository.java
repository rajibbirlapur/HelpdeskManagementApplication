package com.terracis.helpdeskmanagementapp.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.terracis.helpdeskmanagementapp.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>
{
  
	Long countByStatusName(String status);
	
	Page<Ticket> findByStatus_Name(String status, Pageable pageable);
	
	@Query("SELECT t FROM Ticket t WHERE " +
		       "(:status IS NULL OR :status = '' OR t.status.name = :status) AND (" +
		       ":keyword IS NULL OR :keyword = '' OR " +
		       "LOWER(t.ticketNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(t.vendorName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(t.hardwareType.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(t.office.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(t.createdBy.username) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
		       ")")
		Page<Ticket> smartSearch(@Param("keyword") String keyword,
		                         @Param("status") String status,
		                         Pageable pageable);
	
	Page<Ticket> findTicketByStatus_Name(String name, Pageable pageable);
	
}

