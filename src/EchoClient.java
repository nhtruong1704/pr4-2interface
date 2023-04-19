import java.net.*;
import java.io.*;

public class EchoClient {
    private static final String HOSTNAME = "localhost";
    private static final int PORT_NUMBER = 12345;

    public static void main(String[] args) {
        try (Socket echoSocket = new Socket(HOSTNAME, PORT_NUMBER);
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
             PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to ModifiedEchoServer on " + HOSTNAME + ":" + PORT_NUMBER);

            while (true) {
                System.out.print("Enter input type (array/list/string), colon, and input data: ");
                String userInput = stdIn.readLine();

                if (userInput == null) {
                    break;
                }

                out.println(userInput);

                String serverResponse = in.readLine();
                System.out.println("Server response: ");
                System.out.println(serverResponse);
            }

            System.out.println("Closing connection.");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + HOSTNAME);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + HOSTNAME + ":" + PORT_NUMBER);
            System.exit(1);
        }
    }
}
