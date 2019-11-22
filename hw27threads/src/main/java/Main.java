import java.util.concurrent.atomic.AtomicInteger;

public class Main  {

    private AtomicInteger count = new AtomicInteger(0);
    private static final int LIMIT = 100_000_000;
    private String last;
    private boolean isIncrement = false;

    public static void main(String[] args) throws InterruptedException {
        Main counter = new Main();
        counter.go();
    }

    private void inc()  {
        for (int i = 0; i < LIMIT; i++) {
            if (count.get() == 10) {
                isIncrement = false;
            } else if (count.get() == 0) {
                isIncrement = true;
            }
            try {
                while (last.equals(Thread.currentThread().getName())) {
                    this.wait();
                }

                if(isIncrement) {
                    count.incrementAndGet();
                } else {
                    count.decrementAndGet();
                }

                System.out.println(Thread.currentThread().getName() + " " + count);
                last = Thread.currentThread().getName();
                sleep();
                //notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new NotInterestingException(ex);
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void go() throws InterruptedException {
        Thread thread1 = new Thread(this::inc);
        last = thread1.getName();
        Thread thread2 = new Thread(this::inc);
        thread1.setName("Thread-2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("CounterBroken:" + count);
    }

    private class NotInterestingException extends RuntimeException {
        NotInterestingException(InterruptedException ex) {
            super(ex);
        }
    }
}
