import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Guest {
	
	private Connection con;
	
	public Guest(Connection con) throws SQLException {
		this.con = con;
	}
	
	public Connection getConnection() {
		return this.con;
	}

	public int registerUser(String tableType, String username, String pass) throws SQLException {	
		String query="INSERT INTO "+tableType+" (username, password) VALUES" + "(?, ?)";
		PreparedStatement preparedStm = (PreparedStatement) con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStm.setString(1, username);
		preparedStm.setString(2, pass);
	    preparedStm.executeUpdate();    
	    ResultSet rs = preparedStm.getGeneratedKeys();
	    return rs.next() ? rs.getInt(1) : (-1);
	}
	
    public void registerTraveler(int id,String name, String surname, String departureCity) throws SQLException, FileNotFoundException {
    	InputStream in = new FileInputStream("src/traveler.jpg");
		String query="UPDATE Travelers SET name=?,surname=?,departureCity=?,profilePic=? WHERE id=?";
		PreparedStatement preparedStm = (PreparedStatement) con.prepareStatement(query);
		preparedStm.setString(1, name);
		preparedStm.setString(2, surname);
		preparedStm.setString(3, departureCity);
		preparedStm.setBlob(4, in);
		preparedStm.setInt(5, id);
	    preparedStm.execute();
	    preparedStm.close();
	}
	
	public int login(String tableType, String username, String pass) throws SQLException {	
		Statement mystate = con.createStatement();
		String query = "SELECT id FROM "+tableType+" WHERE (username, password) = ('" +username+"','"+pass+"')";
		ResultSet myRS = mystate.executeQuery(query);
		return myRS.next() ? myRS.getInt("id") : (-1);
	}

}
