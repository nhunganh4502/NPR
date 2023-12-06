package Tut8JavaMulticastSockets.BigInteger;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPMulticastPublisher {

    public static void sendUDPMessage(BigInteger message, String ipAddress, int port) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName(ipAddress);
        byte[] msg = message.toByteArray();  // Convert the BigInteger to a byte array
        DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);
        socket.send(packet);
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        int n = 1;
        while (true) {
            BigInteger bigIntegerMessage = BigInteger.valueOf(n);

            sendUDPMessage(bigIntegerMessage, "230.0.0.0", 4321);
            n++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

