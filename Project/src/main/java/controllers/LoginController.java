package controllers;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import jakarta.servlet.http.HttpSession;
import models.User;
import services.AuthService;
import services.LoginService;

public class LoginController implements Controller {
	private LoginService loginService = new LoginService();
	private AuthService authService = new AuthService();
	
	Handler login = (ctx)->{
		User attempt = ctx.bodyAsClass(User.class);
		attempt=loginService.login(attempt, authService.getUsers());
		
		if (attempt!=null ) {
			HttpSession session = ctx.req().getSession();
			session.setAttribute("user", attempt);
			//commented out because I decided to do filters with url instead
			//session.setAttribute("filter", "clear");
			ctx.json("Login successful. Logged on to: "+attempt.getEmail());
			ctx.status(200);//ok
			
		}else {
			ctx.json("Credentials not found");
			ctx.status(401);//401: unauthorized 
		}
	};
	
	Handler logout = (ctx)->{
		HttpSession session = ctx.req().getSession(false);
		if (session!=null) {
			session.invalidate();
			ctx.json("Log out successful");
			ctx.status(200);//ok
		}else {
			ctx.status(400);//bad request
		}
	};
	

	@Override
	public void addRoutes(Javalin app) {
		
		app.post("/login", login);
		app.get("/logout", logout);
	}

}
