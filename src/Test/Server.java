package Test;
import java.math.BigInteger;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(20006);
            System.out.println("Server waiting for clients on port 20006...");

            while (true) {
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from client: " + clientMessage);

                // Process the first message ( tính 2 lần)
                BigInteger result = new BigInteger(clientMessage).multiply(BigInteger.valueOf(2));
                byte[] sendBuffer = result.toString().getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                socket.send(sendPacket);

                // Loop for handling subsequent messages (tính bình phương)
                while (true) {
                    receiveBuffer = new byte[1024];
                    receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    socket.receive(receivePacket);

                    clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                    try {
                        BigInteger number = new BigInteger(clientMessage);
                        BigInteger square = number.pow(2);
                        sendBuffer = square.toString().getBytes();
                    } catch (NumberFormatException e) {
                        // Not a positive integer, send the message back as is
                        sendBuffer = clientMessage.getBytes();
                    }

                    sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                    socket.send(sendPacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
