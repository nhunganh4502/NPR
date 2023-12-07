package Final.MulticastSocket;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Scanner;

public class BigIntegerClient {
    public static void main(String[] args) {
        try {
            // Create a multicast socket on port 20006
            MulticastSocket clientSocket = new MulticastSocket(20006);
            InetAddress group = InetAddress.getByName("224.0.0.1");
            clientSocket.joinGroup(group);

            // Use Scanner to read input from the keyboard
            Scanner scanner = new Scanner(System.in);

            // Send the first message "[abc]" to the server
            sendData(clientSocket, group, "[abc]");

            // Receive and print the first reply from server (2x[abc])
            String response = receiveData(clientSocket);
            System.out.println(response);

            // Loop for client
            while (true) {
                // Read a message from user keyboard
                System.out.print("Enter a message: ");
                String userInput = scanner.nextLine();

                // Send the message to server
                sendData(clientSocket, group, userInput);

                // Receive and print the response from server
                String serverResponse = receiveData(clientSocket);
                System.out.println("Server response: " + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendData(MulticastSocket socket, InetAddress group, String message) throws IOException {
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, group, 20006);
        socket.send(sendPacket);
    }

    private static String receiveData(MulticastSocket socket) throws IOException {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        return new String(receivePacket.getData(), 0, receivePacket.getLength());
    }
}
