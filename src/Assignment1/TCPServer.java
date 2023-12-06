package Assignment1;


import java.io.*;
import java.net.*;

class TCPServer {
    public static void main(String argv[]) throws Exception {
        int clientNumber;
        int squaredNumber;
        ServerSocket welcomeSocket = new ServerSocket(6789);
        System.out.println("Server is waiting to accept user... ");

        while(true) {

            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Accept a client!");

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            clientNumber = inFromClient.read();
            squaredNumber = (int) Math.pow(clientNumber, 2);

            outToClient.writeByte(squaredNumber);
        }
    }
}

