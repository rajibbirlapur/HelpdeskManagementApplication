package com.terracis.helpdeskmanagementapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "tickets")
public class Ticket 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String ticketNumber;
	
	@ManyToOne
	@JoinColumn(name = "hardware_type_id")
	private HardwareType hardwareType;
	
	private String vendorName;
	
	@ManyToOne
	@JoinColumn(name = "office_id")
	private Office office;
	
	@Column(columnDefinition = "TEXT")
	private String issueDescription;
	
	private LocalDate issueDate;
	
	@ManyToOne
	@JoinColumn(name = "status_id")
	private TicketStatus status;
	
	@ManyToOne
	@JoinColumn(name = "created_By")
	private User createdBy;
	
	private LocalDateTime createdAt = LocalDateTime.now();
	private String caseId;
	private String modelNo;

}

