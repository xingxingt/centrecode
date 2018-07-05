package collect;


import java.util.*;

public class HashMapTest {

    public static void main(String[] args) {

        //入数据
        HashMap<String, String> hm = new HashMap<String, String>();
        for (int i = 0; i <= 30; i++) {
            hm.put("" + i, "value " + i);
        }

        //取数据
        Set<String> keyset = hm.keySet();
        for (String key : keyset) {
            System.out.println("key :" + key);
        }
        Collection<String> values = hm.values();
        for (String value : values) {
            System.out.println("value :" + value);
        }

        Set<Map.Entry<String, String>> entrySet = hm.entrySet();
        for (Map.Entry entry : entrySet) {
            System.out.println("key :" + entry.getKey() + "value:" + entry.getValue());
        }


    }
}
