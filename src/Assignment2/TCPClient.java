package Assignment2;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {
    public static void main(String argv[]) throws Exception {
        int number;
        int returnedNumber;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Please enter your message");
            Socket clientSocket = new Socket("localhost", 0006);

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            number = Integer.parseInt(inFromUser.readLine());
            outToServer.writeByte(number);

            returnedNumber = inFromServer.read();
            System.out.println("FROM SERVER: " + returnedNumber);
        }
    }
}
