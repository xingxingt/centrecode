package JVMDemo;

/**
 * 【演示类的加载机制 被动引用】
 * 通过子类引用父类的静态字段，不会导致子类初始化
 */
class SuperClass {
    //todo 用于验证类是否被初始化
    static {
        System.out.println("superClass init");
    }

    public static int value = 123;


}


class subClass extends SuperClass {

    static {
        System.out.println("subClass init");
    }

    public static String sub_value = "super value";


}

/**
 * 非主动(被动)使用类字段
 */
public class StaticLoadDemo {

    public static void main(String[] args) {
        /**
         * 对于静态变量只有直接定义这个字段的类才会被初始化，因此通过其子类引用父类的静态字段，
         * 只会触发父类的初始化而不会触发子类的初始化
         */
//        System.out.println(subClass.value); //结果输出 superClass init  123

        /**
         *并没有触发类的初始化
         */
//        SuperClass[] sc = new SuperClass[10];

        /**
         * 触发类的初始化
         */
        SuperClass sc1 = new SuperClass();


    }
}
