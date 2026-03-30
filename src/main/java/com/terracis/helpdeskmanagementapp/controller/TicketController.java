package com.terracis.helpdeskmanagementapp.controller;

import java.security.Principal;

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

import com.terracis.helpdeskmanagementapp.entity.HardwareType;
import com.terracis.helpdeskmanagementapp.entity.Office;
import com.terracis.helpdeskmanagementapp.entity.Ticket;
import com.terracis.helpdeskmanagementapp.entity.User;
import com.terracis.helpdeskmanagementapp.repo.HardwareTypeRepository;
import com.terracis.helpdeskmanagementapp.repo.OfficeRepository;
import com.terracis.helpdeskmanagementapp.repo.UserRepo;
import com.terracis.helpdeskmanagementapp.service.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController 
{
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private HardwareTypeRepository hardwareRepository;
	
	@Autowired
	private OfficeRepository officeRepository;

	// Show create form
	@GetMapping("/create")
	public String showCreateForm(Model model)
	{
		model.addAttribute("ticket", new Ticket());
		
		model.addAttribute("hardwareList", hardwareRepository.findAll());
		model.addAttribute("officeList", officeRepository.findAll());
		
		return "create-ticket";
	}
	
	// Save ticket
	@PostMapping("/create")
	public String createTicket(@ModelAttribute Ticket ticket, Authentication authentication, RedirectAttributes redirectAttributes)
	{
		// fix hardware
		HardwareType hardware = hardwareRepository.findById(ticket.getHardwareType().getId()).orElse(null);
	    ticket.setHardwareType(hardware);
	    
	    // fix office
	    Office office = officeRepository.findById(ticket.getOffice().getId()).orElse(null);
	    ticket.setOffice(office);
		
		// Set user
		String username = authentication.getName();
		User user = userRepo.findByUsername(username);
		ticket.setCreatedBy(user);
		
	    ticketService.createTicket(ticket);
	    
	    // Add success message
	    redirectAttributes.addFlashAttribute("successMessage", "Ticket created successfully");
	    
	    return "redirect:/dashboard";	
	}
	
	// view all
	@GetMapping("/list")
	public String viewTickets(Model model, 
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String status)
	{
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Ticket> ticketPage = ticketService.getFilteredTickets(keyword, status, pageable);
		
		model.addAttribute("tickets", ticketPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", ticketPage.getTotalPages());
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("status", status);
		
		return "ticket-list";
	}
	
	// edit form
	@GetMapping("/edit/{id}")
	public String editTicket(@PathVariable Long id, Model model)
	{
		Ticket ticket = ticketService.getTicketById(id);
		model.addAttribute("ticket", ticket);
		return "ticket-edit";
	}
	
	// update status
	@PostMapping("/update")
	public String updateTicket(@ModelAttribute Ticket ticket, Principal principal, RedirectAttributes redirectAttributes)
	{
		boolean updated = ticketService.updateTicket(ticket, principal.getName());
		
		if(updated)
		{
			redirectAttributes.addFlashAttribute("successMessage", "Ticket updated successfully!");
		}
		else 
		{
			redirectAttributes.addFlashAttribute("errorMessage", "You are not allowed to update this Ticket!");
		}
		
		return "redirect:/tickets/list";
	}
	
	// delete ticket(Admin only)
	@GetMapping("/delete/{id}")
	public String deleteTicket(@PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes)
	{
		String username = authentication.getName();
		boolean deleted = ticketService.deleteTicket(id, username);
		if(deleted)
			redirectAttributes.addFlashAttribute("successMessage", "Ticket successfully deleted!");
		else 
			redirectAttributes.addFlashAttribute("errorMessage", "You are allowed to delete this ticket");
		
		return "redirect:/tickets/list";
	}
	
	// show resolved tickets
	@GetMapping("/resolvedTickets")
	public String viewResolvedTickets(@RequestParam(defaultValue = "0") int page,
			                          @RequestParam(defaultValue = "5") int size,
			                          Model model)
	{
		Page<Ticket> ticketPage = ticketService.getResolvedTickets(page, size);
		
		model.addAttribute("ticketPage", ticketPage);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", ticketPage.getTotalPages());
		
		return "resolved-tickets";
	}
}

