package Tut4JavaThreads;

public class HelloThread extends Thread {


    @Override
    public void run() {
        int index = 1;

        for (int i = 0; i < 10; i++) {
            System.out.println("  - HelloThread running " + index++);

            try {
                // Sleep 1030 milliseconds.
                Thread.sleep(1030);
            } catch (InterruptedException e) {
            }

        }
        System.out.println("  - ==> HelloThread stopped");
    }
}
