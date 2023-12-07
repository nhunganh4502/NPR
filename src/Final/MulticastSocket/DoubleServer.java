package Final.MulticastSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class DoubleServer {
    private static final String MULTICAST_ADDRESS = "224.0.0.1";
    private static final int PORT = 10010;

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            MulticastSocket multicastSocket = new MulticastSocket(PORT);

            multicastSocket.joinGroup(group);

            System.out.println("Double server is ready...");

            // The double to be sent
            double doubleToSend = 3.14;

            // Convert the double to bytes
            byte[] doubleBytes = Double.toString(doubleToSend).getBytes();

            // Send the double to the multicast group
            DatagramPacket packet = new DatagramPacket(doubleBytes, doubleBytes.length, group, PORT);
            multicastSocket.send(packet);

            System.out.println("Double sent successfully.");

            // Leave the multicast group
            multicastSocket.leaveGroup(group);
            multicastSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
