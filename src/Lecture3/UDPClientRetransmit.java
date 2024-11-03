package Lecture3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class UDPClientRetransmit {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        Scanner scan = new Scanner(System.in);

        try {
            // create a datagram socket to send and receive packets
            socket = new DatagramSocket();

            System.out.println("Message: ");
            String message = scan.nextLine();
            byte[] buffer = message.getBytes();

            // packet the message
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 8088);

            // send to the server
            socket.send(packet);

            // wait for the response
            byte[] responseBuffer = new byte[256];
            DatagramPacket receivedPacket = new DatagramPacket(responseBuffer, responseBuffer.length);

            // set the 5 seconds timeout
            socket.setSoTimeout(5000);
            try {
                socket.receive(receivedPacket); // wait fot the server response
            }
            catch (SocketTimeoutException e) {
                System.out.println("No response within timeout, retrying...");
                socket.send(packet);
            }

            String response = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            System.out.println("Server Response: " + response);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
        finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
