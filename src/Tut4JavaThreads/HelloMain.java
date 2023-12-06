package Tut4JavaThreads;


public class HelloMain {

    public static void main(String[] args) throws InterruptedException {

        int idx = 1;

        for (int i = 0; i < 2; i++) {

            System.out.println("Main thread running " + idx++);
            // Sleep 2101 milliseconds.
            Thread.sleep(2101);
        }

        HelloThread helloThread = new HelloThread();

        // Run thread
        helloThread.start();

        for (int i = 0; i < 3; i++) {
            System.out.println("Main thread running " + idx++);
            // Sleep 2101 milliseconds.
            Thread.sleep(2101);
        }

        System.out.println("==> Main thread stopped");
    }
}
