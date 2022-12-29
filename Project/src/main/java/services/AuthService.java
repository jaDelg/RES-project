package services;

import java.util.List;

import daos.AuthDAO;
import models.User;

public class AuthService {
	private AuthDAO authDAO =new AuthDAO();
	
	public List<User> getUsers(){
		return authDAO.getAllUsers();
	}
	
	public void addUser(User user) {
		authDAO.addUser(user);
		
	}
	
	
}
