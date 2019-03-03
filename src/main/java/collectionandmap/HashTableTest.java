package collectionandmap;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Hashtable也是一个散列表
 * HashTable和HashMap的不同之处在于:
 * 1,HashTable的函数都是同步的，被synchronized修饰
 * 2,HashTable的key和value不允许为空
 */
public class HashTableTest {


    public static void main(String[] args) {

        //入数据
        Hashtable<String, String> ht = new Hashtable<String, String>();
        for (int i = 0; i <= 30; i++) {
            ht.put("" + i, "value " + i);
        }


        //取数据
        Enumeration e = ht.keys();
        while (e.hasMoreElements()) {
            System.out.println(e.nextElement());
        }
        //遍历value
        e = ht.elements();
        while (e.hasMoreElements()) {
            System.out.println(e.nextElement());

        }

    }

}
