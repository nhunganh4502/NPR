package Test2UDPTinhBinhPhuong;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) {
        int port = 10006; // Use the last 3 digits of your student ID
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println("Server is running on port " + port);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            String studentID = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received from client: " + studentID);

            BigInteger studentIDValue = new BigInteger(studentID);
            BigInteger result = studentIDValue.multiply(BigInteger.valueOf(3));
            byte[] sendData = result.toString().getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
            serverSocket.send(sendPacket);

            while (true) {
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from client: " + clientMessage);

                if (clientMessage.matches("\\d+")) {
                    BigInteger number = new BigInteger(clientMessage);
                    BigInteger cubeResult = number.pow(3);
                    sendData = cubeResult.toString().getBytes();
                } else {
                    sendData = clientMessage.getBytes();
                }

                sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
