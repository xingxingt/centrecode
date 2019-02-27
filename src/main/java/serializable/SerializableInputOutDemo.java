package serializable;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class User implements Serializable {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * 【如何将一个Java对象序列化到文件里】
 */
public class SerializableInputOutDemo {


    public void readAndWrite() {
        User user = new User();
        user.setId(1);
        user.setName("Mr XP.Wang");
        // 创建一个List对象  
        List<String> list = new ArrayList<String>();
        list.add("My name");
        list.add(" is");
        list.add(" Mr XP.Wang");

        /**
         * 将对象序列化写入文件
         */
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("C:/wxp.txt"));
            os.writeObject(user);// 将User对象写进文件  
            os.writeObject(list);// 将List列表写进文件  
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * 将对象从文件中读取出来
         */
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("C:/wxp.txt"));
            User temp = (User) is.readObject();// 从流中读取User的数据  
            System.out.println(temp.getId());
            System.out.println(temp.getName());
            List tempList = (List) is.readObject();// 从流中读取List的数据  
            for (Iterator iterator = tempList.iterator(); iterator.hasNext(); ) {
                System.out.print(iterator.next());
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new SerializableInputOutDemo().readAndWrite();
    }

}

