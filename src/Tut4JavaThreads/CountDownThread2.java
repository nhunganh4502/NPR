package Tut4JavaThreads;

public class CountDownThread2 implements Runnable {

    @Override
    public void run() {
        int count = 10;
        for (int i = count; i > 0; i--) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("Time is up");
    }
    public static void main(String[] args) {
        CountDownThread2 countDownThread = new CountDownThread2();
        Thread thread = new Thread(countDownThread);
        thread.start();
    }
}


