package Lecture5;

import java.io.*;
import java.net.*;

public class TCPServer {
    private static int sharedCounter = 0;  // Shared resource

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is listening on port 8080");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // Handle each client connection in a new thread
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                // Simulate accessing the shared resource (without synchronization)
                int localCounter = sharedCounter;
                localCounter++;  // Increment the local copy
                Thread.sleep(100);  // Simulate some processing time
                sharedCounter = localCounter;  // Write back to the shared resource

                out.println("Counter value: " + sharedCounter);

                System.out.println("Updated sharedCounter to " + sharedCounter);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
