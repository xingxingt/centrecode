package test;


import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Test {
    private static Logger log = Logger.getLogger(Test.class.getClass());

    public static void main(String[] args) throws Exception {

        int oldCapacity = 10;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        System.out.println(newCapacity);

        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < 1000000; i++) {
            Thread.sleep(3);
            System.out.println(i);
            map.put("test" + i, "testvalue");
        }


//        Map<String, String> map = new HashMap<String, String>();
//        map.put("test", "testvalue");
//
//        map.put("test", "replace");
//
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }

//        String str1 = "abcd";
//        System.out.println(System.identityHashCode(str1));
//        str1 = "jsgei";
//        System.out.println(System.identityHashCode(str1));
//        String str2 = str1;
//
//
//        System.out.println(System.identityHashCode(str2));


    }
}
