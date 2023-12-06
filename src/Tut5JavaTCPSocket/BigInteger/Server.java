package Tut5JavaTCPSocket.BigInteger;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(10006);
            System.out.println("Server is waiting for connections...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected to a client.");

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            // Read the first message from the client
            String firstMessage = reader.readLine();
            System.out.println("Received from client: " + firstMessage);

            // Process the first message (2 times of [abc])
            BigInteger result = new BigInteger(firstMessage).multiply(BigInteger.valueOf(2));

            // Send the result back to the client
            writer.write(result.toString());
            writer.newLine();
            writer.flush();

            // Server main loop
            while (true) {
                // Receive the client's message
                String clientMessage = reader.readLine();
                System.out.println("Received from client: " + clientMessage);

                // Check if the message is a positive integer
                try {
                    BigInteger number = new BigInteger(clientMessage);
                    // If positive integer, calculate the square
                    result = number.multiply(number);
                    writer.write(result.toString());
                } catch (NumberFormatException e) {
                    // If not a positive integer, send the message back to the client
                    writer.write(clientMessage);
                }

                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
