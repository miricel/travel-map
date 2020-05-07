import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.mysql.jdbc.Blob;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

abstract class User {

	protected int id;
	protected Connection con;
	
	public User(java.sql.Connection con, int id) {
		this.con = (Connection) con;
		this.id = id;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getStringColumn(String tableType, String column) throws SQLException {	
		Statement mystate = con.createStatement();
		String query = "SELECT "+column+" FROM "+tableType;
		query = query + " WHERE id="+id;
		ResultSet myRS = mystate.executeQuery(query);
		if(myRS.next())
			return myRS.getString(1);
		else return null;
		
	}
	
	public int getIntColumn(String tableType, String column) throws SQLException {
		Statement mystate = con.createStatement();
		String query = "SELECT "+column+" FROM "+tableType;
		query = query + " WHERE  id="+id;
		ResultSet myRS = mystate.executeQuery(query);		
		if(myRS.next())
			return myRS.getInt(1);
		else return -1;
	}
	
	public ImageIcon getProfilePicture(String tableType) throws SQLException, IOException {
		Statement mystate = con.createStatement();
		String query = "SELECT profilePic FROM "+tableType;
		query = query + " WHERE  id="+id;
		ResultSet myRS = mystate.executeQuery(query);		
		if(myRS.next()) {
			Blob blob = (Blob) myRS.getBlob("profilePic");
			InputStream is = blob.getBinaryStream();
			BufferedImage bi = ImageIO.read(is);
			Image image = bi;
			ImageIcon ic = new ImageIcon(image);
			return ic;
			}
		return null;
	}
	
	public void uploadPic(String tableType, String path) throws FileNotFoundException, SQLException {
		InputStream in = new FileInputStream(path);
		String query="UPDATE " + tableType + " SET profilePic = ? "+" WHERE id=" + id;
		PreparedStatement mystate = (PreparedStatement) con.prepareStatement(query);
		mystate.setBlob(1, in);
		mystate.execute();
		mystate.close();
	}
	
	public void setStringColumn(String tableType, String column, String newData) throws SQLException {
		String query="UPDATE " + tableType + " SET "+ column + " = ? "+" WHERE id=" + id;
		PreparedStatement mystate = (PreparedStatement) con.prepareStatement(query);
		mystate.setString(1, newData);
		mystate.execute();
		mystate.close();
	}
	
	public void logOut() throws SQLException {
		con.close();
	}
	
}
