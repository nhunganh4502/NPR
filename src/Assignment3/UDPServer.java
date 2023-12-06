package Assignment3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

class UDPServer {
    public static void main(String args[]) throws Exception {
        int port = 0006;
        DatagramSocket serverSocket = new DatagramSocket(port);
        System.out.println("Server is running...");

        while(true) {
            byte[] receiveData = new byte[1024];
            byte[] sendData    = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket (receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            double clientNumber = ByteBuffer.wrap(receivePacket.getData()).getDouble();
            System.out.println("Number from client: " + clientNumber);

            InetAddress IPAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            double cubeValue = Math.pow(clientNumber, 3);
            sendData = ByteBuffer.allocate(8).putDouble(cubeValue).array();
            DatagramPacket sendPacket = new DatagramPacket  (sendData, sendData.length, IPAddress, clientPort);
            serverSocket.send(sendPacket);

        }
    }
}
