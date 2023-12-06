package Final.MulticastSocket;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class BigIntegerClient {
    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName("224.0.0.1");
            int port = 10006;

            MulticastSocket multicastSocket = new MulticastSocket(port);
            multicastSocket.joinGroup(group);

            System.out.println("Multicast client is ready...");

            // Read the initial message from the user
            System.out.print("Enter the initial message: ");
            Scanner scanner = new Scanner(System.in);
            String initialMessage = scanner.nextLine();

            // Send the initial message to the server
            byte[] initialData = initialMessage.getBytes();
            DatagramPacket initialPacket = new DatagramPacket(initialData, initialData.length, group, port);
            multicastSocket.send(initialPacket);

            // Receive and print the first reply from the server
            byte[] replyData = new byte[1024];
            DatagramPacket replyPacket = new DatagramPacket(replyData, replyData.length);
            multicastSocket.receive(replyPacket);

            String firstReply = new String(replyPacket.getData(), 0, replyPacket.getLength());
            System.out.println("Server's reply: " + firstReply);

            // Client main loop
            while (true) {
                // Read a message from the user's keyboard
                System.out.print("Enter a message: ");
                String clientMessage = scanner.nextLine();

                // Send the message to the server
                byte[] clientData = clientMessage.getBytes();
                DatagramPacket clientPacket = new DatagramPacket(clientData, clientData.length, group, port);
                multicastSocket.send(clientPacket);

                // Receive and print the server's reply
                multicastSocket.receive(replyPacket);
                String serverReply = new String(replyPacket.getData(), 0, replyPacket.getLength());
                System.out.println("Server's reply: " + serverReply);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
