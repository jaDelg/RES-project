package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.User;
import utils.ConnectionUtil;

public class AuthDAO {
	
	public List<User> getAllUsers(){
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * From employees;";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			List<User> list = new ArrayList<>();
			
			//creates users from query results and adds them to a list. 
			while (result.next()) {
				User u = new User();
				u.setEmail(result.getString("email"));
				u.setPassword(result.getString("pass"));
				u.setRole(result.getString("emp_role"));
				list.add(u);

			}

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	//adds user with a prepared statement, to avoid sql injection
	public void addUser(User user) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO employees (email, pass, emp_role) VALUES (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getRole());
			
			statement.execute();

		}catch (SQLException e) {
			e.printStackTrace();

		}
		
	}
	
	
	
	//might not need this for now. could be used to change user email, password, and/or role.
	public void updateUser() {
		
	}
}
