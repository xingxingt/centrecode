package copy.shallowcopy;

/**
 * 浅copy
 * 浅拷贝: 浅copy会创建一个新的对象，是对原始对象的一份精确拷贝，如果原始对象中有基本数据类型，则copy的是基本数据类型的值，如果原始对象中存在
 * 引用数据类型，则copy的是引用数据类型的引用地址,如果其中一个对象改变了该引用地址，则另一个对象对象也会发生改变;
 * ref:https://my.oschina.net/jackieyeah/blog/206391#comments
 */
public class CopyTest {

    public static void main(String[] args) {
        // 原始对象
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
