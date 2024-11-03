package Lecture2;

import java.io.IOException;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        int count = 0;

        while (true) {
            // connect to the TCP Server on localhost at the port 8088 (match the server port)
            try (Socket socket = new Socket("localhost", 8088)) {
                System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

                // send a message to the server
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                //out.println("Hello from the client: " + count++);
                out.println("Hello, Server");

                // read the response from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = reader.readLine();
                System.out.println("Server: " + response);
            } catch (UnknownHostException e) {
                System.out.println("Unknown Host");
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}
