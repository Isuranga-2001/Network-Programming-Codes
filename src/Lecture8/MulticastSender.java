package Lecture8;

import java.io.IOException;
import java.net.*;

public class MulticastSender {
    public static void main(String[] args) {
        String multicastAddress = "224.0.0.1";
        int multicastPort = 5000;

        try (MulticastSocket socket = new MulticastSocket(multicastPort)) {
            InetAddress group = InetAddress.getByName(multicastAddress);
            NetworkInterface netIf = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            SocketAddress groupAddress = new InetSocketAddress(group, multicastPort);

            socket.joinGroup(groupAddress, netIf);

            String message = "Hello, Multicast Receivers!";
            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), groupAddress);

            // Send the packet
            socket.send(packet);
            System.out.println("Message sent: " + message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
