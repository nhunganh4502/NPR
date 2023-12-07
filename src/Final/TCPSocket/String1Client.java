package Final.TCPSocket;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Scanner;

public class String1Client {
    public static void main(String[] args) {
        final String serverHost = "localhost";
        Socket socketOfClient = null;
        BufferedWriter os = null;
        BufferedReader is = null;

        try {
            //client yêu cầu server bằng cách tạo một Socket TCP trên máy kèm với địa chỉ IP và port number của tiến tình tương ứng trên máy server.
            socketOfClient = new Socket(serverHost, 7777);

            // tương tự như server input output
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));

            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverHost);
            return;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverHost);
            return;
        }

        try {
            Scanner sc = new Scanner(System.in);
            String enter = sc.nextLine();
            os.write(enter);
            os.newLine();
            // viết tn gửi đến server
            os.write("HELLO! now is " + new Date());
            // insert a newline character in the output stream đảm bảo cho việc đọc dữ liệu ở sêrver chính xác hơn
            os.newLine();
            // đảm bảo dữ liệu được gửi đi ngay
            os.flush();
            os.write("I am Tom Cat");
            os.newLine();
            os.flush();
            os.write("QUIT");
            os.newLine();
            os.flush();

            String responseLine;
            while ((responseLine = is.readLine()) != null) {
                System.out.println("Server: " + responseLine);
                if (responseLine.indexOf("OK") != -1) {
                    break;
                    // nếu = -1 thì là not found , khác trừ 1 thì là found OK
                }
            }

            os.close();
            is.close();
            socketOfClient.close();
        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}