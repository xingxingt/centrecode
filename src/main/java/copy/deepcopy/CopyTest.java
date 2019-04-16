package copy.deepcopy;


/**
 * 深copy
 * 深拷贝：深copy会copy原始对象基本数据类型值和引用对象指向的动态内存,当对象和它引用的对象一起copy时便是深copy;深copy相比浅copy花费的时间久，
 * 花销大;
 * ref:https://my.oschina.net/jackieyeah/blog/206391#comments
 */
public class CopyTest {

    public static void main(String[] args) {
        // 原始对象
        System.out.println("test deep copy");
        Student stud = new Student("John", "Algebra");
        System.out.println("Original Object: " + stud.getName() + " - " + stud.getSubj().getName());

        // 拷贝对象
        Student clonedStud = (Student) stud.clone();
        System.out.println("Cloned Object: " + clonedStud.getName() + " - " + clonedStud.getSubj().getName());

        // 原始对象和拷贝对象是否一样：
        System.out.println("Is Original Object the same with Cloned Object: " + (stud == clonedStud));
        // 原始对象和拷贝对象的name属性是否一样
        System.out.println("Is Original Object's field name the same with Cloned Object: " + (stud.getName() == clonedStud.getName()));
        // 原始对象和拷贝对象的subj属性是否一样
        System.out.println("Is Original Object's field subj the same with Cloned Object: " + (stud.getSubj() == clonedStud.getSubj()));

        //stud.getSubj()指向的只是subject对象的引用地址,而stud和clonedStud指向的是同一个引用地址
        stud.setName("Dan");
        stud.getSubj().setName("Physics");

        System.out.println("Original Object after it is updated: " + stud.getName() + " - " + stud.getSubj().getName());
        System.out.println("Cloned Object after updating original object: " + clonedStud.getName() + " - " + clonedStud.getSubj().getName());
    }
}
