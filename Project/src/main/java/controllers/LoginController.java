package controllers;

import io.javalin.http.Handler;
import jakarta.servlet.http.HttpSession;
import models.User;
import services.AuthService;
import services.LoginService;

public class LoginController {
	private LoginService loginService = new LoginService();
	private AuthService authService = new AuthService();
	
	Handler login = (ctx)->{
		User attempt = ctx.bodyAsClass(User.class);
		attempt=loginService.login(attempt, authService.getUsers());
		
		if (attempt!=null ) {
			HttpSession session = ctx.req().getSession();
			session.setAttribute("user", attempt);
			ctx.json("Login successful. Logged on to: "+attempt.getEmail());
			ctx.status(200);
			
		}else {
			ctx.json("Credentials not found");
			ctx.status(401);//401: unauthorized 
		}
	};

}