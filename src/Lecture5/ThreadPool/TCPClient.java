package Lecture5.ThreadPool;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class TCPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 8088;

        int totalRequests = 1000;
        long totalElapsedTime = 0;

        for (int i = 0; i < totalRequests; i++) {
            try (Socket socket = new Socket(serverAddress, port);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                long startTime = System.nanoTime();
                out.println("Hello Server " + i);
                String response = in.readLine();
                long elapsedTime = System.nanoTime() - startTime;

                totalElapsedTime += elapsedTime;

                System.out.printf("Request %d: Response = %s, Time = %d ms%n", i, response, TimeUnit.NANOSECONDS.toMillis(elapsedTime));
            } catch (IOException e) {
                System.out.println("Connection error: " + e.getMessage());
            }
        }

        long averageElapsedTime = totalElapsedTime / totalRequests;
        System.out.printf("Average Response Time: %d ms%n", TimeUnit.NANOSECONDS.toMillis(averageElapsedTime));

        System.out.printf("Total Elapsed Time: %d ms%n", TimeUnit.NANOSECONDS.toMillis(totalElapsedTime));
    }
}
