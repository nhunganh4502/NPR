package Final.UDPSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class DoubleClient {
    public static void main(String args[]) throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        int port = 5094;
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");

        while (true) {
            byte[] sendData;
            byte[] receiveData = new byte[1024];

            // Get real number from user
            System.out.print("Please enter a real number (or type 'exit' to quit): ");
            String userInput = inFromUser.readLine();

            if (userInput.equalsIgnoreCase("exit")) {
                // If the user types "exit," close the clientSocket and exit the loop
                clientSocket.close();
                break;
            }

            // Convert the user input to bytes and send to the server
            sendData = userInput.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, port);
            clientSocket.send(sendPacket);

            // Receive the cube value from the server
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String cubeResult = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Cube value received from server: " + cubeResult);
        }
    }
}