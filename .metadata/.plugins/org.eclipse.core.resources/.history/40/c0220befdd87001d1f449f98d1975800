package services;

import java.util.List;

import daos.TicketDAO;
import models.Ticket;
import models.User;

public class TicketService {
	private TicketDAO ticketDAO = new TicketDAO();
	
	public List<Ticket> getTickets(){
		return ticketDAO.getAllTickets();
	}
	
	public void addTicket(Ticket ticket, User user) {
		ticketDAO.addTicket(ticket, user);
		// here i have to add a ticket to the current user
	}
	
	//changed this from id to email
	public List<Ticket> findTicketsByEmail(String email){
		//here I need to get the current user and then return their list
		return ticketDAO.findTicketsByEmail(email);
	}
	
	public List<Ticket> findTicketsByEmail(String email, String filter){
		//here I need to get the current user and then return their list
		return ticketDAO.findFilteredTickets(email, filter);
	}
	
	
	
	public boolean updateTicket(Ticket ticket, String updaterEmail) {
		return ticketDAO.updateTicket(ticket.getStatus(), ticket.getTicketNumber(), updaterEmail);
	}
}
