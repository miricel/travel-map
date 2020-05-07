package chat.Client;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private final int serverPort;
    private final String serverName;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();
    private boolean running;
    private Thread thread;

    public Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public void trySimple() throws IOException, InterruptedException, ClassNotFoundException {
        //get the localhost IP address, if server is running on some other IP, you need to use that
        // InetAddress host = InetAddress.getLocalHost();
        //establish socket connection to server
        this.socket = new Socket("192.168.1.9", 8818);
        //write to socket using ObjectOutputStream
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Sending request to Socket Server");
         for(int i=0; i<5;i++) {
            if (i == 4)output.println("exit");
            else {
               output.println("" + i);
            }

            String message;
            if( (message = input.readLine()) != null)
                System.out.println("Message: " + message);
            else System.out.println("ceva nush");
            Thread.sleep(500);
        }
        input.close();
        output.close();
    }

    public void original(Client client) throws IOException {
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

    public void msg(String dest, String text) {
        String cmd = "msg "+ dest + " " + text;
        output.println(cmd);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Client client = new Client( "localhost", 8818);
        client.running = true;
       // client.trySimple();
        client.original(client);

    }

    public boolean connect() {
        try {
            //establish socket connection to server
            this.socket = new Socket( serverName,serverPort );
            System.out.println("Client port: "+ socket.getLocalPort());
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new PrintWriter(socket.getOutputStream(), true);

            return true;
        } catch (IOException e) {
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
            return true;
        }
        else return false;
    }

    private void startMessagReciver() {
        thread = new Thread(){
            @Override
            public void run() {
                readMessageLoop();
            }
        };
        thread.start();
    }

    private void readMessageLoop() {
        String line;
        try{
            while ( ((line = input.readLine()) != null) ) {
                String[] tokens = StringUtils.split(line);
                if( tokens != null && tokens.length > 0 ) {
                    String cmd = tokens[0];
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

        if(thread != null)
            thread.interrupt();
        thread = null;
        running = false;
        output.println("quit");
        try {
            socket.close();
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
