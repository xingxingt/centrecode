package kafka.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * @description: 多线程消费者主程序
 */
public class MultiThreadConsumer {

    public static void main(String[] args) {

        int numConsumers = 3;
        String groupId = "test";
        List<String> topics = Arrays.asList("test");
        final ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

        final List<ConsumerLoop> consumers = new ArrayList<ConsumerLoop>();
        for (int i = 0; i < numConsumers; i++) {
            ConsumerLoop consumer = new ConsumerLoop(i, groupId, topics);
            consumers.add(consumer);
            executor.submit(consumer);
        }


        /**
         * *addshutdownHook钩子函数调用的条件:
         * 1. method called: System.exit(int)
         * 2. ctrl-C pressed on the console.
         * 3. the last non-daemon thread exits.
         * 4. user logoff or system shutdown.
         */
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for (ConsumerLoop consumer : consumers) {
                    consumer.shutdown();
                }
                executor.shutdown();
                try {
                    executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        System.exit(-1);
    }
}
