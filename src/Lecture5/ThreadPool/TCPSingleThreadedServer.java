package Lecture5.ThreadPool;

import java.io.*;
import java.net.*;

public class TCPSingleThreadedServer {
    public static void main(String[] args) {
        int port = 8088;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Single-threaded Server started...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String message = in.readLine();
                    System.out.println("Received: " + message);
                    out.println("Echo: " + message);
                } catch (IOException e) {
                    System.out.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
