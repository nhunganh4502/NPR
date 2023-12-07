package Final.MulticastSocket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class DoubleClient {
    private static final String MULTICAST_ADDRESS = "224.0.0.1";
    private static final int PORT = 10010;

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            MulticastSocket multicastSocket = new MulticastSocket(PORT);

            multicastSocket.joinGroup(group);

            System.out.println("Double client is ready to receive...");

            // Receive the double from the multicast group
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            multicastSocket.receive(packet);

            // Convert the received bytes to a double
            double receivedDouble = Double.parseDouble(new String(packet.getData()).trim());

            // Process the received double (multiply by 2 in this case)
            double result = receivedDouble * 2;

            // Print the result
            System.out.println("Received Double: " + receivedDouble);
            System.out.println("Processed Result: " + result);

            // Leave the multicast group
            multicastSocket.leaveGroup(group);
            multicastSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
