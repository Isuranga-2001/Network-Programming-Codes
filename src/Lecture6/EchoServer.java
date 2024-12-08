package Lecture6;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {
    private static final int PORT = 5001;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()){
            // configure the server channel as non-blocking
            serverSocketChannel.configureBlocking(false);

            // bind the port with the server channel
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            System.out.println("Server listening on port " + PORT);

            // open a selector to handle multiple channels
            Selector selector = Selector.open();

            // register the server channel with the selector to accept events
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            // main server loop
            while (true){
                // wait for events
                selector.select();

                // get the set of selected keys (channels ready for IO operations
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectionKeySet.iterator();

                // iterate for the keys
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();

                    // check of the key's channel is ready to accept a new connection
                    if (key.isAcceptable()){
                        // accept the new client connection
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = serverChannel.accept();

                        // configure the client channel to be non-blocking
                        clientChannel.configureBlocking(false);

                        // Register the client channel with the selector for read events
                        clientChannel.register(selector, SelectionKey.OP_ACCEPT);
                        System.out.println("Connected to Client: " + clientChannel.getRemoteAddress());
                    }
                    else if (key.isReadable()){
                        // read data from the client
                        SocketChannel clientChannel = (SocketChannel) key.channel();

                        // create the buffer for reading data
                        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                        int bytesRead = clientChannel.read(buffer);

                        if (bytesRead > 0){
                            buffer.flip();

                            // echo back to the client
                            clientChannel.write(buffer);
                            buffer.clear();
                        }
                        else if (bytesRead == -1){
                            // the client close the connection
                            System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
                            clientChannel.close();
                        }
                    }

                    // remove the key from selected set
                    keyIterator.remove();
                }
            }

        } catch (IOException exception){
            System.out.println("Exception: " + exception.toString());
        }
    }
}
