package chat.Client;

import com.mysql.jdbc.PreparedStatement;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private final int serverPort;
    private final String serverName;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();
    private Thread thread;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private String username;
    private Connection con;

    public Client(String serverName, int serverPort, Connection con) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.con = con;
    }

    public void original(Client client) throws IOException, SQLException {
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
                client.msg("jim", "Hei Salut! Sunt guest!");
            }
            else System.err.println("Login failed");
            System.out.println("gata?");
        }
    }

    public void msg(String dest, String text) throws SQLException {
        String cmd = "msg "+ dest + " " + text;
        Statement mystate = con.createStatement();
        int conv = 0;
        String query = "SELECT id FROM conversation WHERE ((user1 = '"+username+"' AND user2 = '"+dest+"') " +
                "OR (user1 = '"+dest+"' AND user2 = '"+username+"'))";
        ResultSet myRS = mystate.executeQuery(query);
        if(myRS.next())
            conv = myRS.getInt("id");
        else
        {
            query = "INSERT INTO conversation (user1, user2) VALUES (?, ?)";
            PreparedStatement preparedStm = (PreparedStatement) con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStm.setString(1, username);
            preparedStm.setString(2, dest);
            preparedStm.executeUpdate();
            ResultSet rs = preparedStm.getGeneratedKeys();
            if(rs.next())
                conv = rs.getInt("id");
            else try {
                throw new SQLException();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Can't connect to database!");
            }
            preparedStm.close();
        }

        String sql = "INSERT INTO messages(`text`, `from`, `to`, `conversationID`) VALUES ( ?, ?, ?, ?)";

        PreparedStatement preparedStm = (PreparedStatement) con.prepareStatement(sql);
        preparedStm.setString(1, text);
        preparedStm.setString(2, username);
        preparedStm.setString(3, dest);
        preparedStm.setInt(4, conv);
        preparedStm.execute();
        preparedStm.close();


        output.println(cmd);
    }

  /*  public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Client client = new Client( "localhost", 8818,);
       // client.trySimple();
        client.original(client);

    }

   */

    public boolean connect() {
        try {
            //establish socket connection to server
            this.socket = new Socket( serverName,serverPort );
            System.out.println("Client port: "+ socket.getLocalPort());
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new PrintWriter(socket.getOutputStream(), true);

            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Can't connect to server!");
            e.printStackTrace();

        }
        return false;
    }

    public boolean login(String username, String password) throws IOException {
        String cmd = "login "+username+" "+password;
        output.println(cmd);
        String response = input.readLine();
        System.out.println("Response: "+response);
        if("Login Succes".equalsIgnoreCase(response) ) {
            startMessagReciver();
            this.username = username;
            return true;
        }
        else return false;
    }

    private void startMessagReciver() {
        thread = new Thread(){
            @Override
            public void run() {
                running.set(true);
                readMessageLoop();
            }
        };
        thread.start();
    }

    private void readMessageLoop() {
        String line;
        try{
            while ( running.get() && ((line = input.readLine()) != null) ) {
                String[] tokens = StringUtils.split(line);
                if( tokens != null && tokens.length > 0 ) {
                    String cmd = tokens[0];
                    System.out.println(line);
                    if ("online".equalsIgnoreCase(cmd)){
                        handleOnline(tokens);
                    }else if ("offline".equalsIgnoreCase(cmd))
                        handleOffline(tokens);
                    else if ("msg".equalsIgnoreCase(cmd)){
                        String[] tokensMsg = StringUtils.split(line,null,3);
                        handleMessage(tokensMsg);
                    }
                }
            }
            System.out.println("Gata");
        } catch (IOException e) {
            e.printStackTrace();
        }

      //  disconnect();
    }

    private void handleMessage(String[] tokensMsg) {
        String from = tokensMsg[1];
        String text = tokensMsg[2];

        for( MessageListener listener : messageListeners){
            listener.onMessage(from,text);
        }
    }

    private void handleOffline(String[] tokens) {
        String user = tokens[1];
        for(UserStatusListener listener: userStatusListeners){
            listener.offline(user);
        }
    }

    private void handleOnline(String[] tokens) {
        System.out.println("handleOnline: "+tokens[1]);
        String user = tokens[1];
        for(UserStatusListener listener: userStatusListeners){
           listener.online(user);
        }
    }

    public void addUserStatusListener(UserStatusListener listener){
        userStatusListeners.add(listener);
    }

    public void removeStatusListener(UserStatusListener listener){
        userStatusListeners.remove(listener);
    }

    public void addMessageListener(MessageListener listener){
        messageListeners.add(listener);
    }

    public void removeMessageListener(MessageListener listener){
        messageListeners.remove(listener);
    }

    public void disconnect(){

        running.set(false);
        thread.interrupt();
        thread = null;
        output.println("quit");
        try {
            input.close();
            output.close();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void msgSimple(String jim, String s) {
        String cmd = "msg "+ jim + " " + s;
        output.println(cmd);
    }
}
