package Final.MulticastSocket;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class FileServer {
    private static final String MULTICAST_ADDRESS = "224.0.0.1";
    private static final int PORT = 10007;

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            MulticastSocket multicastSocket = new MulticastSocket(PORT);

            multicastSocket.joinGroup(group);

            System.out.println("File server is ready...");

            // Specify the path to the file to be sent
            String filePath = "path/to/your/file.txt";

            try (BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(filePath))) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    DatagramPacket packet = new DatagramPacket(buffer, bytesRead, group, PORT);
                    multicastSocket.send(packet);
                }

                // Signal the end of the file transfer
                byte[] endSignal = "END_OF_FILE_TRANSFER".getBytes();
                DatagramPacket endPacket = new DatagramPacket(endSignal, endSignal.length, group, PORT);
                multicastSocket.send(endPacket);

                System.out.println("File sent successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

