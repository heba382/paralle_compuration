
class Counter {
    private int count = 0;

    synchronized void increment() {
        count++;
    }

    int getCount() {
        return count;
    }
}

class MyThread extends Thread {
    private Counter counter;

    public MyThread(String name, Counter counter) {
        super(name);
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            System.out.println(getName() + " started.");

            for (int i = 0; i < 1000; i++) {
                counter.increment();

                if (i == 500 && getName().equals("Thread-1")) {
                    throw new RuntimeException("Something went wrong in " + getName());
                }
            }

            System.out.println(getName() + " finished successfully.");
        } catch (Exception e) {
            System.out.println("Caught in " + getName() + ": " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {
            System.out.println("🌍 Global handler caught from " + thread.getName() + ": " + exception.getMessage());
        });

        MyThread t1 = new MyThread("Thread-1", counter);
        MyThread t2 = new MyThread("Thread-2", counter);

        t2.setUncaughtExceptionHandler((thread, exception) -> {
            System.out.println("⚠️ Custom handler for " + thread.getName() + ": " + exception.getMessage());
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("✅ Final Counter Value: " + counter.getCount());
        System.out.println("✅ Main Thread finished.");
    }
}
