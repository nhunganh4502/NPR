package Final.TCPSocket;
import java.io.*;
import java.math.BigInteger;
import java.net.*;

import java.util.Scanner;

public class BigIntClient {
    public static void main(String[] args) {
        final String serverHost = "localhost";
        Socket socketOfClient = null;
        BufferedWriter os = null;
        BufferedReader is = null;

        Scanner sc = null;

        try {
            //client yêu cầu server bằng cách tạo một Socket TCP trên máy kèm với địa chỉ IP và port number của tiến tình tương ứng trên máy server.
            socketOfClient = new Socket(serverHost, 7777);

            // tương tự như server input output
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));

            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

            sc = new Scanner(System.in);


        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverHost);
            return;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverHost);
            return;
        }

        try {

            String userDob;
            while (true) {
                System.out.print("Enter your name (or 'Quit' to exit): ");
                userDob = sc.nextLine();
                os.write(userDob);
                os.newLine();
                os.flush();

                if (userDob.equalsIgnoreCase("Quit")) {
                    break;
                }

                String serverResponse = is.readLine();
                System.out.println("Server: " + serverResponse);
            }




        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        } finally {
            try {
                // Close resources
                is.close();
                os.close();
                socketOfClient.close();
            } catch (IOException e) {
                // Handle exceptions during closing if needed
                e.printStackTrace();
            }
        }
    }



}
