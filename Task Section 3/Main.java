
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Runnable t1 = () -> System.out.println("Task 1 running by " + Thread.currentThread().getName());
        Runnable t2 = () -> System.out.println("Task 2 running by " + Thread.currentThread().getName());
        Runnable t3 = () -> System.out.println("Task 3 running by " + Thread.currentThread().getName());

        MultiExecutor executor = new MultiExecutor(Arrays.asList(t1, t2, t3));
        executor.executeAll();
    }
}
