package Thread;


import java.util.concurrent.*;

public class LiftOff implements Runnable {
    protected int countDown = 10; //Default
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff() {
    }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "(" +
                (countDown > 0 ? countDown : "LiftOff!") + ") ";
    }

    public void run() {
        while (countDown-- > 0) {
            System.out.println(status());
            Thread.yield();
        }

    }
}

/**
 * 使用FixedThreadPool执行任务
 */
class FixedThreadPool {
    public static void main(String[] args) {
        //三个线程来执行五个任务
        ThreadPoolExecutor exec=new ThreadPoolExecutor(3,3,0L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(30));
//          ThreadPoolExecutor exec=new ThreadPoolExecutor(3,3,0L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
//        ExecutorService exec = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 35; i++) {
            exec.execute(new LiftOff());
            exec.submit(new LiftOff());
        }
        exec.shutdown();
    }
}

/**
 * 使用CachedThreadPool执行任务
 */
class CachedThreadPool {
    public static void main(String[] args) {

        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }
}

/**
 * 使用singleThreadExecutor执行任务
 */
class SingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 2; i++) {
            exec.execute(new LiftOff());
        }
    }
}