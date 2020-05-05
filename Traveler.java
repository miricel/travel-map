import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import com.mysql.jdbc.PreparedStatement;

public class Traveler extends User {
	
	public Traveler(java.sql.Connection con, int id) {
		super(con, id);
	}
	
	public void uploadPic(String path) throws FileNotFoundException, SQLException {
		super.uploadPic("Travelers", path);
	}
	
	public ImageIcon getProfilePicture() throws SQLException, IOException {
		return super.getProfilePicture("Travelers");
	}
	
	public void setStringColumn(String column, String newData) throws SQLException {
		super.setStringColumn("Travelers", column, newData);
	}
	
	public int getIntColumn(String column) throws SQLException {
		return super.getIntColumn("Travelers", column);
	}
	
	public String getStringColumn(String column) throws SQLException {
		return super.getStringColumn("Travelers", column);
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

}
