package Final.MulticastSocket;

import java.io.*;
import java.math.BigInteger;
import java.net.*;

public class BigIntegerServer {
    public static void main(String[] args) {
        try {
            // Create a multicast socket on port 20006
            MulticastSocket serverSocket = new MulticastSocket(20006);
            InetAddress group = InetAddress.getByName("224.0.0.1");
            serverSocket.joinGroup(group);

            // Loop for server
            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                if (clientMessage.equals("[abc]")) {
                    // Calculate 2 times of [abc]
                    BigInteger value = new BigInteger("[abc]").multiply(BigInteger.valueOf(2));
                    sendData(serverSocket, group, value.toString());
                } else {
                    try {
                        // Check if the message is a positive integer
                        BigInteger number = new BigInteger(clientMessage);
                        // Calculate the square of [number]
                        BigInteger square = number.multiply(number);
                        sendData(serverSocket, group, square.toString());
                    } catch (NumberFormatException e) {
                        // Not a positive integer, send the client's message back
                        sendData(serverSocket, group, clientMessage);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendData(MulticastSocket socket, InetAddress group, String message) throws IOException {
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, group, 20006);
        socket.send(sendPacket);
    }
}
