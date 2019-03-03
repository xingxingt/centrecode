package collectionandmap;

import java.util.HashMap;
import java.util.UUID;

/**
 * 【HashMap】
 * HashMap底层是使用数据结构实现的
 * 用链表+红黑树解决hash碰撞
 */
public class HashMapDemo1 {


    public static void main(String[] args) throws Exception {

        final HashMap<String, String> map = new HashMap<String, String>(2);
        Thread t = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    new Thread(new Runnable() {
                        public void run() {
                            System.out.println(UUID.randomUUID().toString());
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    }, "ftf" + i).start();
                }
            }
        }, "ftf");
        t.start();
        t.join();

    }
}
