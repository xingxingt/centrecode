package leetcode.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlternatePrint {


    private int n;
    //obj为对象锁
    private Object obj;
    //count % 2 等于 0 就打印foo，等于1就打印bar
    private static int count = 0;

    public AlternatePrint(int n) {
        this.n = n;
        obj = new Object();
    }

    public void foo() throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (obj) {
                //count % 2 == 1本应该是打印bar的，所以就休眠，释放锁
                if (count % 2 == 1) {
                    obj.wait();
                }
                //count % 2 == 0 或者是被唤醒了，就打印foo
//                printFoo.run();
                System.out.print("foo");
                count++;
                //打印完foo，应该去唤醒对面打印bar了
                obj.notify();
            }
        }
    }

    public void bar() throws InterruptedException {
        //休眠10毫秒，确保是foo方法先执行
        Thread.sleep(10);
        for (int i = 0; i < n; i++) {
            synchronized (obj) {
                if (count % 2 == 0) {
                    obj.wait();
                }
//                printBar.run();
                System.out.print("bar");
                count++;
                obj.notify();
            }
        }
    }

    //代码测试
    public static void main(String[] args) {
        AlternatePrint fb = new AlternatePrint(4);

        //用匿名内部类创建一个线程打印foo
        Thread t1 = new Thread() {
            public void run() {
                try {
                    fb.foo();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        //用匿名内部类创建一个线程打印bar
        Thread t2 = new Thread() {
            public void run() {
                try {
                    fb.bar();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        //使用线程池模拟多个线程同时执行
        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.submit(t1);
        executor.submit(t2);

    }
}
