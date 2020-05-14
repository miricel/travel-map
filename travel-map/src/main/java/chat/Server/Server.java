package chat.Server;

import com.mysql.jdbc.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server extends Thread{
    private final int serverPort;
    private ArrayList<ServerWorker> workerList = new ArrayList<ServerWorker>();
    private ServerSocket serverSocket;
    private Lock lock = new ReentrantLock();
    private Connection con;

    public Server(int serverPort, Connection con) {
        this.serverPort = serverPort;
        this.con = con;
    }

    public List<ServerWorker> getWorkerList(){
        return workerList;
    }

    @Override
    public void run() {
        try{
            //create the socket server object
            this.serverSocket = new ServerSocket(serverPort);
            //keep listens indefinitely until receives 'exit' call or program terminates
            while(true){

                System.out.println("About to accept client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from "+clientSocket);

                ServerWorker serverWorker = new ServerWorker(this,clientSocket,con);
                lock.lock();
                workerList.add(serverWorker);
                ArrayList<ServerWorker> all = new ArrayList<ServerWorker>();
                lock.unlock();
                for(ServerWorker unu:all){
                    System.out.println(">"+unu.getLoginUser());
                };
                serverWorker.start();
            }
        }catch(IOException e){
            e.printStackTrace();

        }
        System.out.println("Shutting down Socket server??");
        //close the ServerSocket object
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeWorker(ServerWorker serverWorker) {
        workerList.remove(serverWorker);
    }
}
