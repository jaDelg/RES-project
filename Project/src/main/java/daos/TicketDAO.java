package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Ticket;
import models.User;
import utils.ConnectionUtil;

public class TicketDAO {
	public List<Ticket> getAllTickets() {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * From tickets;";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			List<Ticket> list = new ArrayList<>();

			while (result.next()) {
				Ticket t = new Ticket();
				t.setAmount(result.getDouble("amount"));
				t.setAuthor(result.getString("email"));
				t.setDescription(result.getString("description"));
				t.setrType(result.getString("reintype"));
				t.setStatus(result.getString("status"));
				t.setTicketNumber(result.getInt("ticket_id"));
				list.add(t);

			}

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// adds ticket and sets status to pending.
	public void addTicket(Ticket ticket, User user) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO tickets (amount, description, email, reintype ) VALUES (?,?,?,?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setDouble(index++, ticket.getAmount());
			statement.setString(index++, ticket.getDescription());
			statement.setString(index++, user.getEmail());
			statement.setString(index++, ticket.getrType());
			//no need to add ticket number since that will be set by SQL
			//no need to add a pending status either since sql will set it by default
			statement.execute();

		}

		catch (SQLException e) {
			e.printStackTrace();

		}
	}
	//returns the tickets for the current user (organized by status)
	public List<Ticket> findTicketsByEmail(String email) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM tickets WHERE email = '" + email + "' ORDER BY status;";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			List<Ticket> list = new ArrayList<>();
			while (result.next()) {
				Ticket t = new Ticket();
				t.setAmount(result.getDouble("amount"));
				t.setAuthor(email);
				t.setDescription(result.getString("description"));
				t.setrType(result.getString("reintype"));
				t.setStatus(result.getString("status"));
				t.setTicketNumber(result.getInt("ticket_id"));
				list.add(t);

			}

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// this might be able to be used for a filtered list
	public List<Ticket> findFilteredTickets(String email, String filter) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM tickets WHERE email = '" + email + "' AND reintype= '" + filter
					+ "' ORDER BY status;";

			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			List<Ticket> list = new ArrayList<>();
			while (result.next()) {

				Ticket t = new Ticket();
				t.setAmount(result.getDouble("amount"));
				t.setAuthor(email);
				t.setDescription(result.getString("description"));
				t.setrType(result.getString("reintype"));
				t.setStatus(result.getString("status"));
				t.setTicketNumber(result.getInt("ticket_id"));
				list.add(t);

			}

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public boolean updateTicket(String decision, int ticketID) {
		
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "UPDATE tickets set status=? where ticket_id=?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, decision);
			statement.setInt(index++, ticketID);
			statement.execute();


			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		

	}
}
