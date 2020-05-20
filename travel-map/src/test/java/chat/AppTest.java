package chat;

import static org.junit.Assert.assertTrue;

import Essentials.Transport;
import chat.Client.Client;
import chat.Client.ConnectChatWindow;
import chat.Client.MessageListener;
import chat.Client.UserStatusListener;
import chat.Server.Server;
import chat.Server.ServerMain;
import com.mysql.jdbc.Connection;
import org.junit.Test;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test() throws SQLException, IOException {
        int port = 8818;
        Connection con;
        String url = "jdbc:mysql://localhost:3306/travel-map";
        con= (Connection) DriverManager.getConnection(url, "root", "TravelMap");
        Server server = new Server(port,con);
        server.start();

        Client client = new Client( "localhost", 8818,con);

        client.addUserStatusListener((new UserStatusListener() {
            @Override
            public void online(String username) {
                System.out.println("ONLINE: "+username);
            }

            @Override
            public void offline(String username) {
                System.out.println("OFFLINE: "+username);
            }
        }));
        client.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(String from, String text) {
                System.out.println("Message from "+from+" --> "+text);
            }
        });
        if( !client.connect()){
            System.err.println("Connect failed");
        } else {
            System.out.println("Connect successful");
            if( client.login("guest","password") ) {
                System.out.println("Login successful");
                client.msgSimple("jim", "Hei Salut! Sunt guest!");
            }
            else System.err.println("Login failed");
        }

        System.out.println("gata");
        client.disconnect();

    }

    @Test
    public void test1() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/travel-map";
        Connection con = (Connection) DriverManager.getConnection(url, "root", "TravelMap");
        ResultSet transportData = (new Transport(con,1)).getAllData();

        System.out.println("Type: "+transportData.getString("type"));
        System.out.println("Max tickets: "+transportData.getInt("max_tickets"));
        System.out.println("Reserved tickets: "+transportData.getInt("taken_tickets"));
        System.out.println("From: "+transportData.getString("departure_city"));
        System.out.println("To: "+transportData.getString("destination_city"));
        System.out.println("Departure date, hour: "+transportData.getTimestamp("departure"));
        System.out.println("Arrival date, hour: "+transportData.getTimestamp("arrival"));
        System.out.println("Price: "+transportData.getDouble("price"));

    }
}
