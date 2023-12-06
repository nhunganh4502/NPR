package Assignment3;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class UDPClient {
    public static void main(String args[]) throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        int port = 0006;
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");

        while (true) {
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            System.out.print("Please enter your number: ");
            double number = Double.parseDouble(inFromUser.readLine());
            System.out.println();
            sendData = ByteBuffer.allocate(8).putDouble(number).array();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            double modifiedNumber =  ByteBuffer.wrap(receivePacket.getData()).getDouble();
            System.out.println("FROM SERVER:" + modifiedNumber);

            //clientSocket.close();
        }
    }
}