package JVMDemo;

/**
 * 从JVM调用的角度分析java程序对内存空间的使用:
 * 当JVM进程启动的时候，会从类加载路径中找到包含main方法的入口类HelloJVM
 * 找到该类后直接读取该文件中的二进制数据并且把该类的信息放在运行时的Method内存区域中；
 * 然后会定位到HelloJVM中的main方法的字节码并开始执行main方法中的指令
 * Student student=new Student("liming");
 * 此时会创建student实例对象并且使用student来引用该对象（或者说对该对象命名）其内幕如下：
 * 第一步：JVM会直接到Method区域中查找Student类的信息，此时发现没有Student类，就通过类加载器加载该Student类文件
 * 第二步：在JVM的Method区域加载并找到了Student类之后会在Heap区域中为Student实例对象分配内存
 * 并且在Student的实例对象中持有指向方法区域中的Student类的应用（内存地址）
 * 第三步：JVM实例完后会在当前线程的Stack中的referenc建立实际的应用关系，此时会赋值给student
 * <p>
 * 在JVM中方法的调用一定是属于线程行为，也就是说方法调用本身会发生在调用线程的方法调用栈；
 * 线程的方法调用栈（Method Stack Frames），每一个方法的调用就是方法调用栈中的一个frame，该frame包含了
 * 方法的参数，局部变量，临时数据等；
 * student.sayHello();
 */
public class HelloJvm {

    /**
     * 程序运行的时候会通过反射的方式到Method区域找到入口类的入口方法main
     * main函数也放在方法区域中
     *
     * @param args
     */
    public static void main(String[] args) {
        /**
         * student是放在主线程中的Stack区域中的；
         * Student对象实例是放在所有线程共享的Heap区域中；
         */
        Student student = new Student("liming");

        /**
         * 首先会通过student指针（句柄）找student对象，当找到对象后
         * 会通过对象内部指向方法区域中的指针来调用具体的方法去执行任务
         */
        student.sayHello();

    }

}


class Student {

    /**
     * name本身作为成员变量是放在stack中的，但是name所指向的String对象是放在heap中的
     */
    private String name;

    public Student(String name) {
        this.name = name;
    }

    /**
     * 此方法是放在方法区中的
     */
    public void sayHello() {
        System.out.println(this.name + "  say hello");
    }

}
