package Lecture6;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelReader {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5001;

        try {
            // Open a SocketChannel and connect to the server
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(host, port));

            // Allocate a buffer for reading
            ByteBuffer buffer = ByteBuffer.allocate(256);

            // Read data from the channel
            int bytesRead = socketChannel.read(buffer);
            if (bytesRead > 0) {
                buffer.flip(); // Prepare buffer for reading
                byte[] receivedBytes = new byte[buffer.remaining()];
                buffer.get(receivedBytes); // Extract the data
                System.out.println("Message received from the server: " + new String(receivedBytes));
            } else {
                System.out.println("No data received from the server.");
            }

            // Close the SocketChannel
            socketChannel.close();
        } catch (IOException e) {
            System.err.println("Error occurred while reading data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
