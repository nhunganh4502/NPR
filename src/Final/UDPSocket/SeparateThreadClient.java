package Final.UDPSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class SeparateThreadClient {
    public static void main(String args[]) throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        int port = 9876;
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");

        while (true) {
            byte[] sendData;
            byte[] receiveData = new byte[1024];

            System.out.print("Please enter your message: ");
            String sentence = inFromUser.readLine();

            if (sentence.equalsIgnoreCase("exit")) {
                // If the user types "exit," close the clientSocket and exit the loop
                clientSocket.close();
                break;
            }

            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("FROM SERVER: " + modifiedSentence);
        }

        // Close the client socket when done
        clientSocket.close();
    }
}