package leetcode.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 要求3个线程能够顺序打印 frist second third
 * 对3个线程使用同样的对象锁,
 * 使用lock的wait()使线程让出当前的锁,并处于自旋的状态下
 */
public class OrderPrint {

    private boolean firstFinished;
    private boolean secondFinished;
    private Object lock = new Object(); //对象锁


    public OrderPrint() {

    }


    public void first(Runnable printFirst) throws InterruptedException {

        synchronized (lock) {
            System.out.println("进入first lock");
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            firstFinished = true;
            lock.notifyAll();  //唤醒所有wait中的线程
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {

        synchronized (lock) {
            System.out.println("进入second lock");
            while (!firstFinished) {
                System.out.println("进入second  lock wait!");
                lock.wait();  //使用lock.wait()可以释放锁
            }

            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            secondFinished = true;
            lock.notifyAll();  //唤醒所有wait中的线程
        }
    }

    public void third(Runnable printThird) throws InterruptedException {

        synchronized (lock) {
            System.out.println("进入third lock");
            while (!secondFinished) {
                System.out.println("进入third lock wait!");
                lock.wait(); //使用lock.wait()可以释放锁
            }

            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }


    public void first1() throws InterruptedException {
        synchronized (lock) {
            System.out.println("进入first lock");
            System.out.println("---1");
            firstFinished = true;
            lock.notifyAll();  //唤醒所有wait中的线程
        }
    }

    public void second2() throws InterruptedException {
        synchronized (lock) {
            System.out.println("进入second lock");
            while (!firstFinished) {
                System.out.println("进入second  lock wait!");
                lock.wait();  //使用lock.wait()可以释放锁
            }
            System.out.println("---2");
            secondFinished = true;
            lock.notifyAll();  //唤醒所有wait中的线程
        }
    }

    public void third3() throws InterruptedException {

        synchronized (lock) {
            System.out.println("进入third lock");
            while (!secondFinished) {
                System.out.println("进入third lock wait!");
                lock.wait(); //使用lock.wait()可以释放锁
            }
            System.out.println("---3");
        }
    }


    public static void main(String[] args) {
        OrderPrint op = new OrderPrint();
        Runnable thread1 = () -> {
            try {
                op.first1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable thread2 = () -> {
            try {
                op.second2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable thread3 = () -> {
            try {
                op.third3();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        //使用线程池模拟多个线程同时执行
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(thread3);
        executor.submit(thread1);
        executor.submit(thread2);

    }


}
