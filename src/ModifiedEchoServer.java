import java.net.*;
import java.io.*;
import java.util.*;

public class ModifiedEchoServer {
    private static final int PORT_NUMBER = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            System.out.println("ModifiedEchoServer is running on port " + PORT_NUMBER);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected: " + clientSocket);

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Received message from client: " + inputLine);
                        String[] inputArr = inputLine.split(":");
                        String inputType = inputArr[0];
                        String inputData = inputArr[1];
                        List<Integer> numbers = new ArrayList<>();

                        switch (inputType) {
                            case "array":
                            case "list":
                                String[] dataArr = inputData.split(",");
                                for (String num : dataArr) {
                                    numbers.add(Integer.parseInt(num.trim()));
                                }
                                break;
                            case "string":
                                String[] dataArr2 = inputData.split(":");
                                String delimiter = dataArr2[0];
                                String data = dataArr2[1];
                                String[] dataArr3 = data.split(delimiter);
                                for (String num : dataArr3) {
                                    numbers.add(Integer.parseInt(num.trim()));
                                }
                                break;
                            default:
                                System.err.println("Invalid input type: " + inputType);
                                out.println("Invalid input type: " + inputType);
                                continue;
                        }

                        StringBuilder sb = new StringBuilder();

                        for (int num : numbers) {
                            sb.append("Divisors of " + num + ": ");

                            for (int i = 1; i <= num; i++) {
                                if (num % i == 0) {
                                    sb.append(i + " ");
                                }
                            }
                            sb.append("\n");
                        }

                        String result = sb.toString();
                        out.println(result);
                        System.out.println("Sent result to client: " + result);
                    }

                    System.out.println("Connection closed.");
                } catch (IOException e) {
                    System.err.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + PORT_NUMBER + ": " + e.getMessage());
            System.exit(1);
        }
    }
}
