package Lecture8;

import java.io.IOException;
import java.net.*;

public class MulticastReceiver {
    public static void main(String[] args) {
        String multicastAddress = "224.0.0.1"; // Example multicast address
        int multicastPort = 5000; // Example port

        try (MulticastSocket socket = new MulticastSocket(multicastPort)) {
            InetAddress group = InetAddress.getByName(multicastAddress);
            NetworkInterface netIf = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            SocketAddress groupAddress = new InetSocketAddress(group, multicastPort);

            socket.joinGroup(groupAddress, netIf);
            System.out.println("Joined multicast group: " + multicastAddress);

            // Receive the packet
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String receivedMessage = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Message received: " + receivedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
