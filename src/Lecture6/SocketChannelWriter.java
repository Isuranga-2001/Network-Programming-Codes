package Lecture6;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelWriter {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5001;

        try {
            // Open a SocketChannel and connect to the server
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(host, port));

            // Allocate a buffer for writing
            ByteBuffer buffer = ByteBuffer.allocate(256);

            // Message to send
            String message = "Hello from the writer!";
            buffer.put(message.getBytes());
            buffer.flip(); // Prepare buffer for writing

            // Write data to the channel
            socketChannel.write(buffer);
            System.out.println("Message sent to the server: " + message);

            // Close the SocketChannel
            socketChannel.close();
        } catch (IOException e) {
            System.err.println("Error occurred while writing data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
