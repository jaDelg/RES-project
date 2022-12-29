package controllers;

import io.javalin.Javalin;

public interface Controller {
	public abstract void addRoutes(Javalin app);
}
