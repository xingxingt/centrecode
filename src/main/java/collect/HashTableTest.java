package collect;

import java.util.Enumeration;
import java.util.Hashtable;

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
