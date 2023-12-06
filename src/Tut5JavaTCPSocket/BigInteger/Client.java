package Tut5JavaTCPSocket.BigInteger;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String serverHost = "localhost";

        try {
            Socket socket = new Socket(serverHost, 10006);
            System.out.println("Connected to the server.");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);

            // Read the initial message from the user
            System.out.print("Enter the initial message: ");
            String initialMessage = scanner.nextLine();

            // Send the initial message to the server
            writer.write(initialMessage);
            writer.newLine();
            writer.flush();

            // Receive and print the first reply from the server
            String firstReply = reader.readLine();
            System.out.println("Server's reply: " + firstReply);

            // Client main loop
            while (true) {
                // Read a message from the user's keyboard
                System.out.print("Enter a message: ");
                String clientMessage = scanner.nextLine();

                // Send the message to the server
                writer.write(clientMessage);
                writer.newLine();
                writer.flush();

                // Receive and print the server's reply
                String serverReply = reader.readLine();
                System.out.println("Server's reply: " + serverReply);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
