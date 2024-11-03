package Lecture3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        Scanner scan = new Scanner(System.in);

        try {
            // create a datagram socket on port 8088
            // DatagramSocket, the class that used to send/receive datagram packet without establishing a connection
            socket = new DatagramSocket(8088);
            byte[] buffer = new byte[256];

            // DatagramPacket, represents the data to be sent or received over the network.
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // block and wait for the client request
            socket.receive(packet);

            // extract the data in the packet and process it
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Client: " + received);

            // read the response as input
            System.out.println("Server response: ");
            String response = scan.nextLine();

            byte[] responseData = response.getBytes();

            // packet.getAddress() - get the client's address
            // packet.getPort() - get the port of the process in client
            DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, packet.getAddress(), packet.getPort());

            // delay
            Thread.sleep(10);

            // send a response back to the client
            socket.send(responsePacket);
            System.out.println("Response sent to the client ");
        }
        catch (IOException | InterruptedException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        finally {
            if (socket != null && !socket.isClosed()) {
                // close the connection
                socket.close();
                System.out.println("Socket closed");
            }
        }
    }
}
