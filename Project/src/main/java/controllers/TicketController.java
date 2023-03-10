package controllers;

import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import jakarta.servlet.http.HttpSession;
import models.Ticket;
import models.User;
import services.TicketService;

public class TicketController implements Controller {
	private TicketService ticketService = new TicketService();

	private Handler getAllTickets = (ctx) -> {
		HttpSession session = ctx.req().getSession(false);
		// check to see if a user is loged in
		if (session != null) {
			User user = (User) session.getAttribute("user");
			// checks to see if user is a manager(Only managers can see all tickets)
			if (user.getRole().equals("manager")) {
				List<Ticket> list = ticketService.getTickets();
				ctx.json(list);
				ctx.status(200);// ok
			} else {
				ctx.status(401);// unauthorized
			}

		} else {
			ctx.status(401);
		}
	};

	private Handler addTicket = (ctx) -> {
		HttpSession session = ctx.req().getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user");
			Ticket ticket = ctx.bodyAsClass(Ticket.class);
			ticketService.addTicket(ticket, user);
			ctx.status(201);// created

		} else {
			ctx.status(401);// unauthorized
		}
	};

	private Handler getTicketByUser = (ctx) -> {
		HttpSession session = ctx.req().getSession(false);
		// check to see if a user is loged in
		if (session != null) {
			User user = (User) session.getAttribute("user");
			// take user email and return only their tickets
			List<Ticket> list = ticketService.findTicketsByEmail(user.getEmail());
			ctx.json(list);
			ctx.status(200);// ok

		} else {
			ctx.status(401);
		}
	};

	// here the session is checked to see if loged in.
	// then it checks that the user is a manager, then if they are a manager it
	// checks that the ticket they want to update is not their own
	private Handler updateTicket = (ctx) -> {
		HttpSession session = ctx.req().getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user.getRole().equals("manager")) {
				Ticket decisionTicket = ctx.bodyAsClass(Ticket.class);
				// mainly what you want to take in here, is the ticket number and a status to
				// update the ticket to
				if (ticketService.updateTicket(decisionTicket, user.getEmail())) {
					ctx.status(202);// accepted
				} else {
					ctx.status(400);// bad request
				}
			} else {
				ctx.status(401);// not authorized
			}

		} else {
			ctx.status(401);// not authorized
		}
	};

	Handler getTicketwithFilter = (ctx) -> {
		HttpSession session = ctx.req().getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user");
			String filterString = ctx.pathParam("filter");
			List<Ticket> filteredList = ticketService.findTicketsByEmail(user.getEmail(), filterString);
			ctx.json(filteredList);
			ctx.status(200);

		} else {
			ctx.status(401);
		}

	};
	
	Handler getAllTicketswithFilter = (ctx) -> {
		HttpSession session = ctx.req().getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user");
			// checks to see if user is a manager(Only managers can see all tickets)
			if (user.getRole().equals("manager")) {
				String filterString = ctx.pathParam("filter");
				List<Ticket> list = ticketService.getTickets(filterString);
				ctx.json(list);
				ctx.status(200);// ok
			} else {
				ctx.status(401);// unauthorized
			}

		} else {
			ctx.status(401);
		}

	};

	@Override
	public void addRoutes(Javalin app) {
		app.get("/tickets", getAllTickets);
		app.get("/tickets/{filter}", getAllTicketswithFilter);
		app.get("/mytickets", getTicketByUser);
		app.get("/mytickets/{filter}", getTicketwithFilter);
		app.post("/mytickets", addTicket);
		app.patch("/update", updateTicket);

	}

}
