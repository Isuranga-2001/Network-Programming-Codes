package Lecture4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {
    public static void main(String[] args) {
        try {
            // initiated the URL object with the url
            // different constructors
            // URL(string spec)
            // URL(string protocol, string host, string file)
            // URL(String protocol, String host, int port, String file)
            // URL(URL context, String spec)
            URL url = new URL("https://firststepdowhile.azurewebsites.net/api/Company/GetAllCompanies");

            // different methods in the URL class
            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("User Info: " + url.getUserInfo());
            System.out.println("Host: " + url.getHost());
            System.out.println("Port: " + url.getPort());
            System.out.println("Path: " + url.getPath());
            System.out.println("Query: " + url.getQuery());
            System.out.println("Fragment (Ref): " + url.getRef());
            System.out.println("Authority: " + url.getAuthority());

            // open the connection
            // HttpURLConnection, a subclass of URLConnection abstract class
            HttpURLConnection http_conn = (HttpURLConnection) url.openConnection();
            // or, URLConnection conn = url.openConnection();

            // define the http request attributes
            http_conn.setRequestMethod("GET");
            http_conn.setRequestProperty("Accept", "application/json");

            // set connection timeout and read timeout
            http_conn.setConnectTimeout(5000);

            int responseCode = http_conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader input_buffered_reader = new BufferedReader(new InputStreamReader(http_conn.getInputStream()));

                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = input_buffered_reader.readLine()) != null) {
                    response.append(inputLine);
                }

                // close the buffer
                input_buffered_reader.close();

                System.out.println(response);
            } else {
                System.out.println("Get Request Failed. Response Code: " + responseCode);
            }

            // terminate the connection
            http_conn.connect();
        } catch (MalformedURLException e){
            System.out.println("Malformed URL: " + e.getMessage());
        } catch (IOException e){
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
}
