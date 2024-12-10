package Lecture7;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.*;

import static Lecture5.MultiThreadTCPServer.handleRequest;

public class SecuredTCPServer {
    public static void main(String[] args) {
        try {
            // load the keystore
            KeyStore keyStore = KeyStore.getInstance("JKS");

            FileInputStream keyStoreIS = new FileInputStream("keystore.jks");
            keyStore.load(keyStoreIS, "password".toCharArray());

            // Initialize KeyManagerFactory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, "password".toCharArray());

            // Initialize SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, null);

            // Create SSLServerSocket
            SSLServerSocketFactory ssf = sslContext.getServerSocketFactory();
            SSLServerSocket sslServerSocket = (SSLServerSocket) ssf.createServerSocket(5000);
            System.out.println("SecuredTCPServer listening on port 5000");

            while (true) {
                SSLSocket clientSocket = (SSLSocket) sslServerSocket.accept();

                // create a thread using anonymous class
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(clientSocket);
                    }
                });

                // start the thread
                thread.start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
