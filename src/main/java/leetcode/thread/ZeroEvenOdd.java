package leetcode.thread;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

class IntConsumer {

    public void accept(int n) {
        System.out.print(n);
    }

}

/**
 * 打印零与奇偶数
 */
public class ZeroEvenOdd {

    private int n;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    private Semaphore z = new Semaphore(1);
    private Semaphore e = new Semaphore(0);
    private Semaphore o = new Semaphore(0);

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        System.out.println("print zero");
        for (int i = 0; i < n; i++) {   //打印0
            z.acquire();
            printNumber.accept(0);
            if (i % 2 == 0) {   //判断i的奇偶性
                o.release();
            } else {
                e.release();
            }
        }

    }

    public void even(IntConsumer printNumber) throws InterruptedException {
//        System.out.println("print even");
        for (int i = 2; i <= n; i += 2) { //打印偶数
            e.acquire();
//            System.out.println("run even");
            printNumber.accept(i);
            z.release();
        }


    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
//        System.out.println("print odd");
        for (int i = 1; i <= n; i += 2) { //打印奇数
            o.acquire();
            printNumber.accept(i);
            z.release();

        }
    }


    public static void main(String[] args) {

        ZeroEvenOdd zeo = new ZeroEvenOdd(10);
        IntConsumer ic = new IntConsumer();
        Runnable thread1 = () -> {
            try {
                zeo.zero(ic);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        };
        Runnable thread2 = () -> {
            try {
                zeo.even(ic);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        };
        Runnable thread3 = () -> {
            try {
                zeo.odd(ic);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.submit(thread1);
        pool.submit(thread2);
        pool.submit(thread3);
        pool.shutdown();

    }

}
