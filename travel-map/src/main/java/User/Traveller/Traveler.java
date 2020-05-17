package User.Traveller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import com.mysql.jdbc.PreparedStatement;
import User.User;

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
    
	public boolean isCorrectPassword(String password) throws SQLException {
		return super.isCorrectPassword("Travelers", password);
	}

}