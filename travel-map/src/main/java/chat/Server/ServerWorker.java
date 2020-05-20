package chat.Server;

import com.mysql.jdbc.Connection;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private Lock lock = new ReentrantLock();
    private String loginUser = null;
    private Integer userid = null;
    private PrintWriter output;
    private BufferedReader input;
    private HashSet<String> groups = new HashSet<>();
    private Connection con;

    public ServerWorker(Server server, Socket clientSocket, Connection con) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.con = con;
    }

    public String getLoginUser() {
        return loginUser;
    }

    @Override
    public void run() {
        try {
           // trySimple();
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void trySimple() throws IOException {
        try {
            this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.output = new PrintWriter(clientSocket.getOutputStream(), true);

            String line;
            while( (line = input.readLine()) != null ) {
                System.out.println("Message Received: " + line);
                output.println("send " + line);

                if (line.equalsIgnoreCase("exit")) break;
            }
            input.close();
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Shutting down SocketWorker!!");
    }


    private void handleClientSocket() throws IOException, SQLException {
        this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.output = new PrintWriter(clientSocket.getOutputStream(), true);

        String line;
        while( (line=input.readLine()) != null ){
            System.out.println(line);
            String[] tokens = StringUtils.split(line);
            if( tokens != null && tokens.length > 0 ){
                String cmd = tokens[0];
                if( "logoff".equalsIgnoreCase(cmd) || "quit".equalsIgnoreCase(cmd) ) {
                    handleLogoff();
                    break;
                }
                else if("login".equalsIgnoreCase(cmd)) {
                        handleLogin(output, tokens);
                }
                else if("msg".equals(cmd)) {
                /**/
                    String[] tokensMsg = StringUtils.split(line,null,3);
                    handleMessage(tokensMsg);
                }
                else if("join".equalsIgnoreCase(cmd)){
                        handleJoin(tokens);
                }
                else if("leave".equalsIgnoreCase(cmd)){
                    handleLeave(tokens);
                }
                else{
                        String error = "unknown "+cmd;
                        output.println(error);
                }
            }
        }
        input.close();
        output.close();
        clientSocket.close();
    }

    private void handleLeave(String[] tokens) {
        if(tokens.length > 1){
            String group = tokens[1];
            groups.remove(group);
        }
    }

    public boolean isMembeOfGoup(String group){
        return groups.contains(group);
    }

    private void handleJoin(String[] tokens) {
        if(tokens.length > 1){
            String group = tokens[1];
            groups.add(group);
        }
    }

    private void handleMessage(String[] tokens) throws IOException, SQLException {
        String dest = tokens[1];
        String text = tokens[2];

        boolean isGroup = (dest.charAt(0) == '#');//dest.startsWith("#");
        List<ServerWorker> workerList = server.getWorkerList();

        for(ServerWorker worker: workerList){
            if(isGroup){
                if(worker.isMembeOfGoup(dest)){
                    String message = "msg "+ dest + ":" + loginUser + " " + text;
                    worker.send(message);
                }
            }
            else if(dest.equals(worker.getLoginUser())){

                String message = "msg "+ loginUser + " " + text;
                worker.send(message);
            }

        }
    }

    private void handleLogoff() throws IOException {
        server.removeWorker(this);
        List<ServerWorker> workerList = server.getWorkerList();
        //send current user state to all other online users
        String msgOnline = "offline "+loginUser ;
        for(ServerWorker worker: workerList){
            if( !loginUser.equals(worker.getLoginUser()) )
                worker.send(msgOnline);
        }
        clientSocket.close();
    }

    private boolean loginOK(String username, String password) throws SQLException {

        Statement mystate = con.createStatement();
        String query = "SELECT id FROM travelers WHERE (username, password) = ('" +username+"','"+password+"')";
        ResultSet myRS = mystate.executeQuery(query);
        if( myRS.next()) {
            this.userid = myRS.getInt("id");
            return true;
        } else {
             query = "SELECT id FROM agencies WHERE (username, password) = ('" +username+"','"+password+"')";
             myRS = mystate.executeQuery(query);
            if( myRS.next()) {
                this.userid = myRS.getInt("id");
                return true;
            }else if(username.equalsIgnoreCase("guest") && password.equalsIgnoreCase("password")) {
                return true;
            }
            else try {
                    throw new SQLException();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null,"Invalid username/password");
                    e.printStackTrace();
                }
            }

        return false;
    }

    private void handleLogin(PrintWriter printOutput, String[] tokens) throws IOException, SQLException {
        if(tokens.length == 3){
            String username = tokens[1];
            String password = tokens[2];


            if(loginOK(username,password)){
                String msgSucces = "Login Succes";
                printOutput.println(msgSucces);
                loginUser = username;

                lock.lock();
                List<ServerWorker> workerList = server.getWorkerList();
                lock.unlock();

                //send current user state to all other online users
                String msgOnline = "online " + loginUser;
                for(ServerWorker worker: workerList){
                    if( !loginUser.equals(worker.getLoginUser()) )
                        worker.send(msgOnline);
                }

                //send current user all other online users state
                System.out.println();
                for(ServerWorker worker: workerList){
                    if( worker.getLoginUser()!=null && !loginUser.equals(worker.getLoginUser()) ) {
                        String msgAlreadyOnline = "online " + worker.getLoginUser();
                        send(msgAlreadyOnline);
                    }
                }


            }else {
                String msgFail = "Login Fail";
                printOutput.println(msgFail);
                System.err.println("Login failed\n");
            }
        }
    }

    private void send(String message) throws IOException {
        System.out.println("send:"+ message);
        if(loginUser != null)
            output.println(message);
    }
}
