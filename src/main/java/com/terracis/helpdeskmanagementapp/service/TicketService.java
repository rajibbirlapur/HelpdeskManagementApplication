package com.terracis.helpdeskmanagementapp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.terracis.helpdeskmanagementapp.entity.Ticket;
import com.terracis.helpdeskmanagementapp.entity.TicketStatus;
import com.terracis.helpdeskmanagementapp.exception.ResourceNotFoundException;
import com.terracis.helpdeskmanagementapp.repo.TicketRepository;
import com.terracis.helpdeskmanagementapp.repo.TicketStatusRepository;

@Service
public class TicketService 
{
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketStatusRepository ticketStatusRepository;

    // create ticket
    public void createTicket(Ticket ticket)
    {
    	String ticketNumber = "TKT-" + java.time.LocalDate.now().toString().replace("-", "") 
                + "-" + (int)(Math.random() * 1000);
    	
        ticket.setTicketNumber(ticketNumber);

        ticket.setCreatedAt(LocalDateTime.now());
        
        TicketStatus status = ticketStatusRepository.findByNameIgnoreCase("OPEN");

        if (status == null) {
            throw new ResourceNotFoundException("Default status OPEN not found in DB");
        }

        ticket.setStatus(status);

        ticketRepository.save(ticket);
    }

    // get all tickets
    public Page<Ticket> getAllTickets(Pageable pageable) 
    {
        return ticketRepository.findAll(pageable);
    }

    // get ticket by ID
	public Ticket getTicketById(long id) 
	{
		return ticketRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + id));
	}
	
	// get filtered tickets
	public Page<Ticket> getFilteredTickets(String keyword, String status, Pageable pageable)
	{
	    return ticketRepository.smartSearch(keyword, status, pageable);
	}

	// update status only with PERMISSION + VALIDATION
	public boolean updateTicket(Ticket updatedTicket, String username) 
	{
		Ticket existingTicket = ticketRepository.findById(updatedTicket.getId()).
				orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
		
		if(existingTicket == null) 
			return false;
		
		boolean isOwner = existingTicket.getCreatedBy().getUsername().equals(username);
		boolean isAdmin = username.equals("admin");
		
		if(!isOwner && !isAdmin)
			return false;
		
		TicketStatus status = ticketStatusRepository.findById(updatedTicket.getStatus().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid status Id"));
		
		if(status == null)
			return false;
		
		 if(!isAdmin) 
		 {
		        // If NOT admin → restrict
		        if(status.getName().equalsIgnoreCase("RESOLVED")) 
		        {
		            return false; //  block user
		        }
		    }
		
		existingTicket.setStatus(status);
		
		ticketRepository.save(existingTicket);
		return true;
	}

	// delete ticket through admin only
	public boolean deleteTicket(Long id, String username) 
	{
		Ticket ticket = ticketRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID " + id));
		
		if(ticket == null)
			return false;
		
		 //  Only ADMIN can delete
		boolean isAdmin = username.equals("admin");
		
		if(!isAdmin)
			return false;
		
		ticketRepository.delete(ticket);
		return true;
	}
}