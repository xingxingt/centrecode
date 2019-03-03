package collectionandmap;

import java.util.HashSet;
import java.util.Set;


/**
 * 【HashSet】
 * HashSet不包含重复数据(底层利用HashMap的key来存数据,HashMap的key是不允许重复的)
 * 其实HashSet底层是基于HashMap(散列表来实现的)
 */
public class HashSetDemo {

    public static void main(String[] args) {

        Set<String> set = new HashSet<>();

        set.add("11111");
        set.add("22222");
        set.add("33333");
        set.add("44444");
        set.add("22222");

        System.out.println(set.size());

        for (String e : set) {
            System.out.println(e);
        }
    }

}
