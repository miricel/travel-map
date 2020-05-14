package User.Agency;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import com.mysql.jdbc.PreparedStatement;
import User.User;

public class Agency extends User {

    public Agency(java.sql.Connection con, int id) {
        super(con, id);
    }

    public void uploadPic(String path) throws FileNotFoundException, SQLException {
        super.uploadPic("agencies", path);
    }

    public ImageIcon getProfilePicture() throws SQLException, IOException {
        return super.getProfilePicture("agencies");
    }

    public void setStringColumn(String column, String newData) throws SQLException {
        super.setStringColumn("agencies", column, newData);
    }

    public int getIntColumn(String column) throws SQLException {
        return super.getIntColumn("agencies", column);
    }

    public String getStringColumn(String column) throws SQLException {
        return super.getStringColumn("agencies", column);
    }

    public void showTickets(int travelerID, String s) {

    }

    public void createTicket(String title, String comment) throws SQLException {
        /***/
        String query="INSERT INTO tickets (travelerID, title, comment) VALUES" + "(?, ?, ?)";
        PreparedStatement preparedStm = (PreparedStatement) con.prepareStatement(query);
        preparedStm.setInt(1, id);
        preparedStm.setString(2, title);
        preparedStm.setString(3, comment);
        preparedStm.execute();
        preparedStm.close();

    }

}