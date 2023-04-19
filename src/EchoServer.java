import java.net.*;
import java.io.*;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("EchoServer is running on port 12345");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 12345");
            System.exit(1);
        }

        Socket clientSocket = null;

        try {
            System.out.println("Waiting for client connection...");
            clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received message from client: " + inputLine);
            out.println(inputLine);
            System.out.println("Echoed message to client: " + inputLine);
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();

        System.out.println("Connection closed.");
    }
}
