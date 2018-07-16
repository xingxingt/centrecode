package JVMDemo;

/**
 * 【被动使用类字段】
 * 常量在编译阶段会存入被调类的常量池中，本质上并没有引用到直接定义常量的类
 * 因此不会触发定义常量的类的初始化
 */
class constClass {
    static {
        System.out.println("constClass init");
    }

    public static final int value = 123;
}


public class ConstLoadDemo {

    public static void main(String[] args) {
        System.out.println(constClass.value);//输出结果 123
    }

}


