package Lecture2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        // create a new TCP Server on port 8088
        try (ServerSocket serverSocket = new ServerSocket(8088)) {
            System.out.println("Server is running...");

            while (true){
                // accept the connection from the client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                // read the client request
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String msg = reader.readLine();
                System.out.println("Client: " + msg);

                // send the response to the client
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true); // true for auto-flush
                writer.println("Hello Client!");

                // for the manual flush(), auto-flush should be false
                // the flush() function clears the buffer by sending all data to the client at once
                // writer.flush();

                // close the connection
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while creating the server socket: " + e.getMessage());
        }
    }
}
