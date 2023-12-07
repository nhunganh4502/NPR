package Final.TCPSocket;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class String1Server {
    public static void main(String args[]) throws IOException {

        ServerSocket listener = null;
        System.out.println("Server is waiting to accept user...");
        // khơi tạo 1 biến để đếm thứ tự các client
        int clientNumber = 0;
        try {
            // Đầu tiên phía Server tạo Socket được ràng buộc với một cổng (port number) để chờ nhận yêu cầu từ phía client.
            listener = new ServerSocket(7777);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        try {
            // iệc sử dụng vòng lặp vô hạn cho phép máy chủ xử lý đồng thời nhiều kết nối máy khách mà không thoát ra sau khi xử lý một máy khách
            // dùng while true để nó luôn chấp nhận yêu cầu từ khách hàng trừ khi server bị dừng hoặc sự cố xảy ra
            while (true) {
                //chờ yêu cầu từ client và chấp thuận
                Socket socketOfServer = listener.accept();
                // new thread for new client bắt đầu
                new ServiceThread(socketOfServer, clientNumber++).start();
            }
        } finally {
            // Bất kể có xaỷ ra exception hay không thì vãn đóng máy chủ và giải pháng tài nguyên 1 cách nhẹ nhàng

            listener.close();
        }

    }


    // method này đơn giản chỉ là để in ra message gọi ra dùng cho tiện
    private static void log(String message) {
        System.out.println(message);
    }


    // Thiết kế để kết nối và xử lý yêu cầu của một máy khách cụ thể
    private static class ServiceThread extends Thread {

        private int clientNumber;
        // Là socket mà chờ để accept
        private Socket socketOfServer;


        // constructor được tạo khi có client mới, nó sẽ in hiển thị trong console của server qua mehtod log
        public ServiceThread(Socket socketOfServer, int clientNumber) {
            this.clientNumber = clientNumber;
            this.socketOfServer = socketOfServer;

            // Log
            log("New connection with client# " + this.clientNumber + " at " + socketOfServer);
        }


        // mehtod run này để specify code cần thực thi khi mà thread đó bắt đầu
        @Override
        public void run() {

            try {
                //convert byte streams to character streams.inputStreamReader như cầu nối
                InputStreamReader inputStreamReader = new InputStreamReader(socketOfServer.getInputStream());
                //provide buffering and efficient reading of lines from the socket.
                BufferedReader is = new BufferedReader(inputStreamReader);
                //BufferedReader is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socketOfServer.getOutputStream());
                BufferedWriter os = new BufferedWriter(outputStreamWriter);
                //BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));

                while (true) {

                    String line = is.readLine();
                    os.write(">> " + line.toLowerCase());

                    os.newLine();
                    os.flush();

                    if (line.equals("QUIT")) {
                        // Viết dữ liệu vào socket tloi khách
                        os.write(">> OK");
                        // ensures that the response is properly terminated with a newline.
                        os.newLine();
                        //Xóa luồng đầu ra để đảm bảo dữ liệu được gửi ngay lập tức.
                        os.flush();
                        break;
                    }
                }

            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }
}