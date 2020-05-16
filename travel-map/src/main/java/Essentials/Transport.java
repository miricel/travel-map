package Essentials;

import java.sql.*;
import java.time.LocalDateTime;

public class Transport extends Element {

    public Transport(java.sql.Connection con, int id) {
        super(con, id);
    }

    public void setStringColumn(String column, String newData) throws SQLException {
        super.setStringColumn("transport", column, newData);
    }

    public int getIntColumn(String column) throws SQLException {
        return super.getIntColumn("transport", column);
    }

    public String getStringColumn(String column) throws SQLException {
        return super.getStringColumn("transport", column);
    }

    public LocalDateTime getDateTimeColumn(String column) throws SQLException {
        Timestamp temp = super.getDateTimeColumn("transport", column);
        return new Timestamp(temp.getTime()).toLocalDateTime();
    }

    protected ResultSet getAllData() throws SQLException{
        return super.getAllData("transport");
    }


}
