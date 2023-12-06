package Assignment2;

import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(0006);

            while (true) {
                // Wait for a client to connect
                Socket connectionSocket = serverSocket.accept();

                // Create a new thread to handle the client
                Thread clientThread = new Thread(() -> {
                    try {
                        int clientNumber;
                        int squaredNumber;

                        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                        clientNumber = inFromClient.read();
                        squaredNumber = (int) Math.pow(clientNumber, 2);

                        outToClient.writeByte(squaredNumber);

                        // Close the client socket
                        connectionSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // Start the client thread
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
