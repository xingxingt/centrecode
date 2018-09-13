package kafka.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 *【Kafka producer Demo】
 */
public class ProdectDemo {


    public static void main(String[] args) {

        Properties props = new Properties();
        //broker地址
        props.put("bootstrap.servers", "node1:9092,node2:9092,node3:9092");
        //请求时候需要验证
        props.put("acks", "all");
        //请求失败时候需要重试
        props.put("retries", 0);
        //指定消息key序列化方式
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        //指定消息本身的序列化方式
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");


        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        ProducerRecord<String, String> recoder=new ProducerRecord<String, String>("test","test1","test value");
        try {
            producer.send(recoder);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
