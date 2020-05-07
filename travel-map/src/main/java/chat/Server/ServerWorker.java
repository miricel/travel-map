package chat.Server;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private String loginUser = null;
    private PrintWriter output;
    private BufferedReader input;
    private HashSet<String> groups = new HashSet<>();

    public ServerWorker(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
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


    private void handleClientSocket() throws IOException {
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

    private void handleMessage(String[] tokens) throws IOException {
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

    private void handleLogin(PrintWriter printOutput, String[] tokens) throws IOException {
        if(tokens.length == 3){
            String username = tokens[1];
            String password = tokens[2];
            if((username.equals("guest") && password.equals("password")) || (username.equals("jim") && password.equals("jim")) ){
                String msgSucces = "Login Succes";
                printOutput.println(msgSucces);
                loginUser = username;
                List<ServerWorker> workerList = server.getWorkerList();

                //send current user state to all other online users
                String msgOnline = "online " + loginUser;
                for(ServerWorker worker: workerList){
                    if( !loginUser.equals(worker.getLoginUser()) )
                        worker.send(msgOnline);
                }

                //send current user all other online users state
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
