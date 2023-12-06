package Test1;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 12110;

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server is listening on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket.getInetAddress());

            Thread clientThread = new ServerThread(clientSocket);
            clientThread.start();
        }
    }
}

class ServerThread extends Thread {
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());

            String studentID = inFromClient.readLine();
            BigInteger studentIDValue = new BigInteger(studentID);

            BigInteger result = studentIDValue.multiply(BigInteger.valueOf(3));
            outToClient.writeBytes(result.toString() + "\n");

            while (true) {
                String message = inFromClient.readLine();

                if (isPositiveInteger(message)) {
                    BigInteger num = new BigInteger(message);
                    BigInteger cube = num.pow(3);
                    outToClient.writeBytes(cube.toString() + "\n");
                } else {
                    outToClient.writeBytes(message + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isPositiveInteger(String str) {
        try {
            BigInteger num = new BigInteger(str);
            return num.compareTo(BigInteger.ZERO) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
