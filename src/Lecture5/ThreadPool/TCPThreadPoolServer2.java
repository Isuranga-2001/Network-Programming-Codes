package Lecture5.ThreadPool;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class TCPThreadPoolServer2 {
    public static void main(String[] args) {
        int port = 8088;
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Thread Pool Server (Size 2) started...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String message = in.readLine();
            System.out.println("Received: " + message);
            out.println("Echo: " + message);
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }
}
