package Lecture5;

import java.io.*;
import java.net.*;
import java.util.Random;

public class TCPClient {
    public static void main(String[] args) {
        Random random = new Random();

        // Define how many requests the client will send
        int numberOfRequests = 5;

        for (int i = 0; i < numberOfRequests; i++) {
            try (Socket socket = new Socket("localhost", 8080);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                // Receive the counter value from the server
                String response = in.readLine();
                System.out.println("Server response: " + response);

                // Generate a random delay between 500ms to 2000ms (0.5 to 2 seconds)
                int delay = 500 + random.nextInt(1500);
                System.out.println("Waiting for " + delay + " milliseconds before sending the next request...");
                Thread.sleep(delay);  // Sleep for the randomly generated time

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Client interrupted");
            }
        }
    }
}
