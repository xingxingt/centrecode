package collectionandmap;

import java.util.Iterator;
import java.util.LinkedHashMap;


/**
 * 【LinkedHashMap】
 * LinkedHashMap继承自HashMap，它主要是用链表实现来扩展HashMap类，
 * HashMap中条目是没有顺序的，但是在LinkedHashMap中元素既可以按照它们插入图的顺序排序，
 * 也可以按它们最后一次被访问的顺序排序。
 * ref:https://www.jianshu.com/p/63e76826e852
 */
public class LinkedHashMapDemo {

    public static void main(String[] args) {

        LinkedHashMap<Integer, String> lhm = new LinkedHashMap<Integer, String>();

        lhm.put(1, "1");
        lhm.put(4, "4");
        lhm.put(2, "2");
        lhm.put(3, "3");

        Iterator it = lhm.entrySet().iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }


    }


}
