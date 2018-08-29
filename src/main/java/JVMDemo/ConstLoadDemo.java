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
        /**
         * TODO:constClass没有初始化的原因：
         * 虽然引用了constClass.value，但是在编译阶段通过常量的传播优化，已经将值123 存放在了ConstLoadDemo的常量池中
         * 随后ConstLoadDemo对常量constClass.value的引用实际上已经转化成了ConstLoadDemo类对自身对常量池的引用
         * 也就是说，实际上ConstLoadDemo文件之中并没有constClass类符号的引用入口，这两个类在编译成class文件后就不存在联系了
         */
        System.out.println(constClass.value);//输出结果 123
    }

}


