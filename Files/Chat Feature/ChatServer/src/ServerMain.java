import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        int port = 1234;
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                System.out.println("About to accept client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from "+clientSocket);
                ServerWorker serverWorker = new ServerWorker(clientSocket);
                serverWorker.start();
            }
        }catch(IOException e){
            e.printStackTrace();

        }
    }


}
