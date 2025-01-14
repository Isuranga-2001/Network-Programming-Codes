package Lecture1;

import java.net.Socket;

public class PortScanner {
    public static void main(String[] args) {
        for (int i = 8080; i < 9000; i++) {
            try {
                Socket socket = new Socket("localhost", i);
                System.out.println("Connected to: " + i);
                socket.close();
            } catch (Exception e) {
                System.out.println("Cannot connect to: " + i);
            }
        }

    }
}
