package Final.MulticastSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class LowerCaseServer {
    private static final String MULTICAST_ADDRESS = "224.0.0.1";
    private static final int PORT = 10012;

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            MulticastSocket multicastSocket = new MulticastSocket(PORT);

            multicastSocket.joinGroup(group);

            System.out.println("String server is ready...");

            // Get the string from the user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter a string: ");
            String enteredString = reader.readLine();

            // Convert the entered string to lowercase
            String lowercaseString = enteredString.toLowerCase();

            // Convert the string to bytes
            byte[] stringBytes = lowercaseString.getBytes();

            // Send the string to the multicast group
            DatagramPacket packet = new DatagramPacket(stringBytes, stringBytes.length, group, PORT);
            multicastSocket.send(packet);

            System.out.println("Lowercase String sent successfully.");

            // Leave the multicast group
            multicastSocket.leaveGroup(group);
            multicastSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
