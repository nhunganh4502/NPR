package Final.UDPSocket;

import java.math.BigDecimal;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class DoubleServer {
    public static void main(String args[]) throws Exception {
        int port = 5094;
        DatagramSocket serverSocket = new DatagramSocket(port);
        System.out.println("Server is running...");

        while (true) {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            // Extract the real number from the received data
            String receivedString = new String(receivePacket.getData(), 0, receivePacket.getLength());
            BigDecimal bigDecimal = new BigDecimal(receivedString);
            BigDecimal cube = bigDecimal.pow(3);

            //double receivedNumber = Double.parseDouble(receivedString);

            // Calculate the cube of the number
            //double cubeResult = Math.pow(receivedNumber, 3);

            // Convert the result to bytes and send it back to the client
            String resultString = cube.toString();
            byte[] sendData = resultString.getBytes();
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);

            System.out.println("Cube value sent to client: " + cube);
        }
    }
}