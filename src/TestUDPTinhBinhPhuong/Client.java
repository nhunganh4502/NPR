package TestUDPTinhBinhPhuong;

import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");

            Scanner scanner = new Scanner(System.in);

            // Send the first message to the server (nhập mã sinh viên)
            System.out.print("Enter a message: ");
            String message = scanner.nextLine();
            byte[] sendBuffer = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 20006);
            socket.send(sendPacket);

            // Receive and print the first reply from the server ( nhận 2 lần)
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            String serverReply = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server reply: " + serverReply);

            // Loop for handling subsequent messages
            while (true) {
                // Send the message to the server
                System.out.print("Enter a message: ");
                message = scanner.nextLine();
                sendBuffer = message.getBytes();
                sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 20006);
                socket.send(sendPacket);

                // Receive and print the reply from the server ( nhận và in bình phương)
                receiveBuffer = new byte[1024];
                receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String serverMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server reply: " + serverMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
