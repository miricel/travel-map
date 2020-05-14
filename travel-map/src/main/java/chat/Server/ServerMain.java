package chat.Server;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerMain {
    public static void main(String[] args) throws SQLException {
        int port = 8818;
        Connection con;
        String url = "jdbc:mysql://localhost:3306/travel-map";
        con= (Connection) DriverManager.getConnection(url, "root", "TravelMap");
        Server server = new Server(port,con);
        server.start();
    }


}
