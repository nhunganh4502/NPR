package Test1;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        int port = 12110;

        Socket socket = new Socket("localhost", port);
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.print("Enter your Student ID: ");
        String studentID = inFromUser.readLine();
        outToServer.writeBytes(studentID + "\n");

        String reply = inFromServer.readLine();
        System.out.println("Received from server: " + reply);

        while (true) {
            System.out.print("Enter a message: ");
            String message = inFromUser.readLine();

            outToServer.writeBytes(message + "\n");

            String serverResponse = inFromServer.readLine();
            System.out.println("Received from server: " + serverResponse);
        }
    }
}