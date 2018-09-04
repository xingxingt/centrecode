package timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 【Timer Demo】
 */
public class TimerDemo {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new Task(), 1000L);
        System.out.println("main Thread。。。。。");
    }
}

class Task extends TimerTask {
    public void run() {
        System.out.println("task1执行");
        System.gc();
    }
}

