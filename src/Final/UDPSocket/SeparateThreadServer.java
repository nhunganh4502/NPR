package Final.UDPSocket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class SeparateThreadServer {
    public static void main(String args[]) throws Exception {
        int port = 1094;
        DatagramSocket serverSocket = new DatagramSocket(port);
        System.out.println("Server is running...");

        while (true) {
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            // Create a new thread to handle the client request
            Thread clientHandlerThread = new ClientHandlerThread(serverSocket, receivePacket);
            clientHandlerThread.start();
        }
    }
}

class ClientHandlerThread extends Thread {
    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;

    public ClientHandlerThread(DatagramSocket serverSocket, DatagramPacket receivePacket) {
        this.serverSocket = serverSocket;
        this.receivePacket = receivePacket;
    }

    @Override
    public void run() {
        try {
            String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Message from client: " + sentence);

            InetAddress IPAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
            byte[] sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
            serverSocket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
