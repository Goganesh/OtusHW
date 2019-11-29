public class Main implements Runnable {

    private String trueMessage = "";

    public static void main(String[] args) throws InterruptedException {
        Main counter = new Main();
        counter.go();
    }

    private void go() throws InterruptedException {
        Thread thread1 = new Thread(this);
        Thread thread2 = new Thread(this);
        thread1.setName("Thread-2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 1; i < 11; i++) {
                printCounter(i, Thread.currentThread().getName());
            }
            for (int k = 9; k > 1; k--) {
                printCounter(k, Thread.currentThread().getName());
            }
        }
    }

    private synchronized void printCounter(int counter, String message) {
        if (trueMessage.equals(message)) {
            wait(this);
        }
        System.out.println(Thread.currentThread().getName() + ": " + counter);
        trueMessage = message;
        sleep(1_000);
        notifyAll();
    }

    private static void wait(Object object) {
        try {
            object.wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
