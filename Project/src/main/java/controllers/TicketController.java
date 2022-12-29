package controllers;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import services.TicketService;

public class TicketController implements Controller {
	private TicketService ticketService = new TicketService();
	
	private Handler getAllTickets =(ctx)->{
		
	};
	
	private Handler addTicket =(ctx)->{
		
	};

	private Handler getTicketByUser =(ctx)->{
		
	};
	
	private Handler updateTicket =(ctx)->{
		
	};

	@Override
	public void addRoutes(Javalin app) {
		app.get("/tickets", getAllTickets);
		app.get("/mytickets", getTicketByUser);
		app.post("/mytickets", addTicket);
		app.patch("/update", updateTicket);
		
	}
	
	
}
