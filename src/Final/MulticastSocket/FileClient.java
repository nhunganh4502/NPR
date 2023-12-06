package Final.MulticastSocket;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class FileClient {
    private static final String MULTICAST_ADDRESS = "224.0.0.1";
    private static final int PORT = 10007;

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            MulticastSocket multicastSocket = new MulticastSocket(PORT);

            multicastSocket.joinGroup(group);

            System.out.println("File client is ready to receive...");

            // Specify the path to save the received file
            String outputPath = "path/to/save/receivedFile.txt";

            try (BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(outputPath))) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet;

                while (true) {
                    packet = new DatagramPacket(buffer, buffer.length);
                    multicastSocket.receive(packet);

                    if (new String(packet.getData(), 0, packet.getLength()).equals("END_OF_FILE_TRANSFER")) {
                        System.out.println("File received successfully.");
                        break;
                    }

                    fileOutputStream.write(packet.getData(), 0, packet.getLength());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
