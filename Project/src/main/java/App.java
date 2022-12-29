import controllers.AuthController;
import controllers.Controller;
import controllers.LoginController;
import controllers.TicketController;
import io.javalin.Javalin;

public class App {
	private static Javalin app;
	
	public static void main(String[] args) {
		app= Javalin.create();
		
		configure(new AuthController(), new TicketController(), new LoginController());
		app.start();
	}
	
	
	
	public static void configure(Controller... controllers) {
		for(Controller c:controllers) {
			c.addRoutes(app);
		}
	}
}
