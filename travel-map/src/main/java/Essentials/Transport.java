package Essentials;

import java.sql.*;
import java.time.LocalDateTime;

public class Transport extends Element {
    public Transport(java.sql.Connection con, int id) {
        super(con, id);
    }

    public static void addTransport(String price,
                                    String capaciy,
                                    String trmean,
                                    String depCity,
                                    String arCity,
                                    String depDay,
                                    String arDay,
                                    String depH,
                                    String arH, int idAgency,Connection con) throws SQLException {
        String query="INSERT INTO transport (type, max_tickets, agnecies_id, departure_city, destination_city, price, " +
                "arrival, departure) VALUES (?, ?, ?, ? , ?, ? , ?, ?)";
        com.mysql.jdbc.PreparedStatement preparedStm = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStm.setString(1, trmean);
        preparedStm.setString(2, capaciy);
        preparedStm.setInt(3, idAgency);
        preparedStm.setString(4, depCity);
        preparedStm.setString(5,arCity);
        preparedStm.setDouble(6, Double.parseDouble(price));
        preparedStm.setString(7, arDay+" "+arH);
        preparedStm.setString(8, depDay+" "+depH);

        preparedStm.executeUpdate();

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
