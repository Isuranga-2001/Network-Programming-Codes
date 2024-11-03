package Lecture5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTCPServer {
    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(8888)){
            // connection initialization
            System.out.println("Listening on port 8888...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MultiThreadTCPServer.handleRequest(socket);
                    }
                });

                executor.submit(thread);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            // shutdown the executor service
            executor.shutdown();
        }
    }
}
