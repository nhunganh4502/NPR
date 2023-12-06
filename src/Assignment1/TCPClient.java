package Assignment1;

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String argv[]) throws Exception {
        int number;
        int returnedNumber;

        BufferedReader inFromUser = new BufferedReader(new  InputStreamReader(System.in));
        while(true) {
            System.out.println("Please enter your message");
            Socket clientSocket = new Socket("localhost", 6789);

            DataOutputStream outToServer = new DataOutputStream  (clientSocket.getOutputStream());

            BufferedReader inFromServer = new BufferedReader(new  InputStreamReader(clientSocket.getInputStream()));

            number = Integer.parseInt(inFromUser.readLine());
            outToServer.writeByte(number);

            returnedNumber = inFromServer.read();
            System.out.println("FROM SERVER: " + returnedNumber);
        }
    }
}
