package Essentials;

import java.sql.*;
import java.time.LocalDateTime;

public class Ticket extends Element{

    public Ticket(java.sql.Connection con, int id) {
        super(con, id);
    }


    public void setStringColumn(String column, String newData) throws SQLException {
        super.setStringColumn("tickets", column, newData);
    }

    public int getIntColumn(String column) throws SQLException {
        return super.getIntColumn("tickets", column);
    }

    public String getStringColumn(String column) throws SQLException {
        return super.getStringColumn("tickets", column);
    }

    public LocalDateTime getDateTimeColumn(String column) throws SQLException {
        Timestamp temp = super.getDateTimeColumn("transport", column, getIntColumn("transport_id"));
        return new Timestamp(temp.getTime()).toLocalDateTime();
    }

    protected ResultSet getAllData() throws SQLException{
        return super.getAllData("tickets");
    }

/**
    public void giveReview(String title, String comment) throws SQLException {

        String query="INSERT INTO Reviews (travelerID, title, comment) VALUES" + "(?, ?, ?)";
        PreparedStatement preparedStm = (PreparedStatement) con.prepareStatement(query);
        preparedStm.setInt(1, id);
        preparedStm.setString(2, title);
        preparedStm.setString(3, comment);
        preparedStm.execute();
        preparedStm.close();

    }
 */

}