package Final.MulticastSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class LowercaseClient {
    private static final String MULTICAST_ADDRESS = "224.0.0.1";
    private static final int PORT = 10012;

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            MulticastSocket multicastSocket = new MulticastSocket(PORT);

            multicastSocket.joinGroup(group);

            System.out.println("String client is ready to receive...");

            // Receive the string from the multicast group
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            multicastSocket.receive(packet);

            // Convert the received bytes to a string
            String receivedString = new String(packet.getData(), 0, packet.getLength());

            // Print the received string
            System.out.println("Received String: " + receivedString);

            // Leave the multicast group
            multicastSocket.leaveGroup(group);
            multicastSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
