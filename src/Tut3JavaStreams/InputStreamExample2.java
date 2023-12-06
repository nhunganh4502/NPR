package Tut3JavaStreams;

import java.io.FileInputStream;
import java.io.InputStream;

public class InputStreamExample2 {

    public static void main(String[] args) {
        try {
            InputStream in = new FileInputStream("data.txt");


            byte[] temp = new byte[10];
            int i = -1;

            while ((i = in.read(temp)) != -1) {
                String s = new String(temp, 0, i);
                System.out.println(s);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
