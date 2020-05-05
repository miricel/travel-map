import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.PreparedStatement;

public class Traveler extends User {
	
	public Traveler(java.sql.Connection con, int id) {
		super(con, id);
	}
	
	
	public void showTickets(int travelerID, String s) {
		
	}
	
	public void giveReview(String title, String comment) throws SQLException {
		
		String query="INSERT INTO Reviews (travelerID, title, comment) VALUES" + "(?, ?, ?)";
		PreparedStatement preparedStm = (PreparedStatement) con.prepareStatement(query);
		preparedStm.setInt(1, id);
		preparedStm.setString(2, title);
		preparedStm.setString(3, comment);
	    preparedStm.execute();
	    preparedStm.close();
	        
	}
	
	public void updateDepartureCity(String pass, String newDepartureCity) throws SQLException {
		
		Statement mystate = con.createStatement();
		String query="UPDATE Travelers SET departureCity ="+newDepartureCity+"WHERE (id,password)=("+id+","+pass+")";
		ResultSet myRS = mystate.executeQuery(query);
		myRS.close();
		mystate.close();
	}
	
	public ResultSet getReviews() throws SQLException {
		
		Statement mystate = con.createStatement();
		String query = "SELECT Travelers.name, Travelers.surname, Reviews.comment FROM Reviews, Travelers";
		query = query + "WHERE Reviews.travelerID = Travelers.id";
		ResultSet myRS = mystate.executeQuery(query);
	
		return myRS;
	}

	public ResultSet getUserData() throws SQLException {
		
		Statement mystate = con.createStatement();
		String query = "SELECT * FROM Travelers";
		query = query + "WHERE  id="+id;
		ResultSet myRS = mystate.executeQuery(query);
		
		return myRS;
		
	}

}
