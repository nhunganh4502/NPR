package Final.UDPSocket;



import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
public class BigIntServer {


    public static void main(String[] args) {
            int port = 1094; // Use the last 3 digits of your student ID
            try (DatagramSocket serverSocket = new DatagramSocket(port)) {
                System.out.println("Server is running on port " + port);

                byte[] receiveData = new byte[1024];
                //tạo biến receivePacket để nhận gói tin từ socket
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                //nhận gói tin qua phương thức receive()
                serverSocket.receive(receivePacket);
                //Chuyển dữ liệu vừa nhận về dạng String
                String studentID = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from client: " + studentID);

                // Convert to BigInterger

                BigInteger studentIDValue = new BigInteger(studentID);
                BigInteger result = studentIDValue.multiply(BigInteger.valueOf(3));
                byte[] sendData = result.toString().getBytes();

                //receivePacket.getAddress() lấy địa chỉ từ bên gửi
                //InetAddress IPAddress = receivePacket.getAddress();
                // gửi dứ liệu đi cới đầy đủ thông tin số cổng, địa chỉ của client
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);

                while (true) {

                    receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);
                    String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Received from client: " + clientMessage);
                    // kiểm tra xem đầu vào có phải chỉ chứa số từ 0-9 và có 1 nhất 1 chữ số đúng không , nếu đúng thì chuyển sang big int
                    if (clientMessage.matches("\\d+")) {
                        BigInteger number = new BigInteger(clientMessage);
                        BigInteger cubeResult = number.pow(3);
                        //convert bigint sang string rồi getBuyte để biến thành byte rồi gửi
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
