package kafka.demo;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

/**
 * 单线程消费者的线程实现类
 * 如何优雅的退出consumer
 * https://www.jianshu.com/p/6e89db47c116
 */
public class QuitConsumer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "node1:9092");
        props.put("group.id", "test");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        final KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Arrays.asList("test"));

        final Thread mainThread = Thread.currentThread();

        /**
         * 退出循环需要通过另一个线程调用consumer.wakeup()方法
         * 调用consumer.wakeup()可以退出poll(),并抛出WakeupException异常
         * 我们不需要处理 WakeupException,因为它只是用于跳出循环的一种方式
         * consumer.wakeup()是消费者唯一一个可以从其他线程里安全调用的方法
         * 如果循环运行在主线程里，可以在 ShutdownHook里调用该方法
         *
         * *addshutdownHook钩子函数调用的条件:
         * 1. method called: System.exit(int)
         * 2. ctrl-C pressed on the console.
         * 3. the last non-daemon thread exits.
         * 4. user logoff or system shutdown.
         */
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Starting exit...");
                consumer.wakeup();
                try {
                    // 主线程继续执行，以便可以关闭consumer，提交偏移量
                    mainThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("topic = " + record.topic() + ", partition = " + record.partition()
                            + ", offset = " + record.offset()+",key="+record.key()+",value="+record.value());

                }
                consumer.commitAsync();
            }
        }catch (WakeupException e) {
            // 不处理异常
        } finally {
            // 在退出线程之前调用consumer.close()是很有必要的，它会提交任何还没有提交的东西，并向组协调器发送消息，告知自己要离开群组。
            // 接下来就会触发再均衡，而不需要等待会话超时。
            consumer.commitSync();
            consumer.close();
            System.out.println("Closed consumer and we are done");
        }

        System.out.println("main thread stop!!!");
    }



}
