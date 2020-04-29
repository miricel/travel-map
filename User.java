import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.Connection;

abstract class User {

	protected int id;
	protected Connection con;
	
	public User(java.sql.Connection con, int id) {
		this.con = (Connection) con;
		this.id = id;
	}
	
	public void updatePassword(String tableType, String oldPass, String newPass) throws SQLException {
		
		Statement mystate = con.createStatement();
		String query="UPDATE "+tableType+" SET pass ="+newPass+"WHERE (id,pass)=("+id+","+oldPass+")";
		ResultSet myRS = mystate.executeQuery(query);
		mystate.close();
		myRS.close();
		
	}
	
	public void logOut() throws SQLException {
		con.close();
	}
	
	public abstract ResultSet getUserData()throws SQLException;
	
	
}
