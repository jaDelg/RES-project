package controllers;

import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import models.User;
import services.AuthService;

public class AuthController implements Controller {
	private AuthService authService = new AuthService();

	private Handler getAllUsers = (ctx) -> {
		List<User> list = authService.getUsers();
		ctx.json(list);
		ctx.status(200);
	};
	
	private Handler addUser = (ctx) -> {
		// takes user email, password and role.
		// then checks to see if the email is in use already
		User u = ctx.bodyAsClass(User.class);
		boolean dne = true;
		List<User> list = authService.getUsers();
		for (User checkUser : list) {
			if (checkUser.getEmail().equals(u.getEmail())) {
				dne = false;
				break;
			}
		}
		if (dne) {
			authService.addUser(u);
			ctx.status(201);// created
		} else {
			ctx.json("This email is already in use.");
			ctx.status(400);// bad request
		}

	};

	@Override
	public void addRoutes(Javalin app) {
		app.post("/register", addUser);
		app.get("/register", getAllUsers);

	}

}
