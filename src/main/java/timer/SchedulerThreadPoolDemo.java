package timer;

import util.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 【SchedulerThreadPool Demo】
 */
public class SchedulerThreadPoolDemo {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void main(String[] args) {
        ScheduledExecutorService ses = new ScheduledThreadPoolExecutor(2);
        Task1 task1 = new Task1();
        Task2 task2 = new Task2();


        ses.scheduleAtFixedRate(task1, 0L, 5L, TimeUnit.SECONDS);
        ses.scheduleAtFixedRate(task2, 0L, 5L, TimeUnit.SECONDS);
        System.out.println(sdf.format(new Date()) + "main");
    }
}


class Task1 implements Runnable {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public void run() {
        try {
            HttpUtil.sendGetOnly("http://www.baidu.com");
            System.out.println(sdf.format(new Date()) + "task1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Task2 implements Runnable {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public void run() {
        try {
            HttpUtil.sendGetOnly("http://www.baidu.com");
            System.out.println(sdf.format(new Date()) + "task2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}