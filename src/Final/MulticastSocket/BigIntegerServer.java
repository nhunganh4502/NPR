package Final.MulticastSocket;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class BigIntegerServer {
    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName("224.0.0.1");
            int port = 10006;

            MulticastSocket multicastSocket = new MulticastSocket(port);
            multicastSocket.joinGroup(group);

            System.out.println("Multicast server is ready...");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet);

                String clientMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received from client: " + clientMessage);

                // Process the first message (2 times of [abc])
                BigInteger result = new BigInteger(clientMessage).multiply(BigInteger.valueOf(2));

                // Send the result back to the client
                byte[] replyData = result.toString().getBytes();
                DatagramPacket replyPacket = new DatagramPacket(replyData, replyData.length, group, port);
                multicastSocket.send(replyPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
