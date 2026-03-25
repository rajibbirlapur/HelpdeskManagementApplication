package com.terracis.helpdeskmanagementapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.terracis.helpdeskmanagementapp.repo.TicketRepository;

@Controller
public class DashboardController 
{
	@Autowired
	private TicketRepository ticketRepo;
	
	@GetMapping("/dashboard")
	public String dashboard(Model model, Authentication authentication)
	{
		String username = authentication.getName();
		model.addAttribute("username" , username);
		
		model.addAttribute("totalTickets", ticketRepo.count());
		model.addAttribute("openTickets", ticketRepo.countByStatusName("OPEN"));
		model.addAttribute("inProgressTickets", ticketRepo.countByStatusName("IN_PROGRESS"));
		model.addAttribute("resolvedTickets", ticketRepo.countByStatusName("RESOLVED"));
		
		return "dashboard";
	}

}
