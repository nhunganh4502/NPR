package Test2UDPTinhBinhPhuong;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Change this to the IP or hostname of the server if needed
        int serverPort = 10006; // Use the server port

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {
            InetAddress serverIPAddress = InetAddress.getByName(serverAddress);

            // Step 1: Send Student_ID and receive the first server response
            String studentID = "2001040006"; // Replace with your student ID
            sendRequest(socket, studentID, serverIPAddress, serverPort);
            String serverResponse = receiveResponse(socket);
            System.out.println("Received from server: " + serverResponse);

            // Step 2: Continue to read and send messages
            while (true) {
                System.out.print("Enter a message: ");
                String clientMessage = scanner.nextLine();
                sendRequest(socket, clientMessage, serverIPAddress, serverPort);

                // Step 3: Handle server responses
                serverResponse = receiveResponse(socket);
                System.out.println("Received from server: " + serverResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendRequest(DatagramSocket socket, String message, InetAddress serverAddress, int serverPort) {
        try {
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String receiveResponse(DatagramSocket socket) {
        try {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            return new String(receivePacket.getData(), 0, receivePacket.getLength());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
