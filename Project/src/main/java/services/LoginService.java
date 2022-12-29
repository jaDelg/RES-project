package services;

import java.util.List;

import models.User;

public class LoginService {
	public User login(User userChecking, List<User> users) {
		// checks user login credentials against the database info to see if there's a
		// match. If not, then returns null.
		for (User user : users) {
			if (userChecking.equals(user)) {
				// I'll need the right role later, so this should keep the login role consistent
				userChecking.setRole(user.getRole());
				return user;
			}
		}
		return null;
	}

}
