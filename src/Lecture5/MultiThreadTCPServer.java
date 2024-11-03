package Lecture5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadTCPServer {
    public static int COUNT = 0;

    public static void main(String[] args) throws IOException {
        // create a new TCP Server on port 5006
        try (ServerSocket serverSocket = new ServerSocket(5006)) {
            System.out.println("Server is running and waiting for connections...");

            while (true){
                // accept the connection from the client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress().getHostAddress());

                // create a thread using anonymous class
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(clientSocket);
                    }
                });

                // start the thread
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while creating the server socket: " + e.getMessage());
        }
    }

    protected static void handleRequest(Socket clientSocket) {
        // data communication
        BufferedReader reader = null;

        try {
            // read the client request
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String msg = reader.readLine();
            int num = Integer.parseInt(msg);

            // processing data
            // use the synchronized block to avoid race condition
            synchronized (MultiThreadTCPServer.class) {
                COUNT += num;
            }

            String server_msg = "Count: " + COUNT;
            System.out.println(server_msg);

            // send the response to the client
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true); // true for auto-flush
            writer.println(server_msg);

            // close the connection
            clientSocket.close();
        }
        catch (Exception e) {
            System.out.println("Error occurred while handling request: " + e.getMessage());
        }
    }
}
