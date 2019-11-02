package annotation.demo;


import java.lang.annotation.*;

/**
 * 【自定义注解】
 *
 * 1,Target是这个注解的作用域，ElementType.METHOD是这个注解的作用域的列表，METHOD是方法声明，除此之外，还有：
 * CONSTRUCTOR（构造方法声明）,FIELD（字段声明）,LOCAL VARIABLE（局部变量声明）,METHOD（方法声明）,PACKAGE（包声明）,PARAMETER（参数声明）,TYPE（类接口）
 * 2,Retention是它的生命周期，前面不是说注解按照运行机制有一个分类嘛，RUNTIME就是在运行时存在，可以通过反射读取。除此之外，还有:
 * SOURCE（只在源码显示，编译时丢弃）,CLASS（编译时记录到class中，运行时忽略）,RUNTIME（运行时存在，可以通过反射读取）
 * 3,Inherited是一个标识性的元注解，它允许子注解继承它。
 * 4,Documented，生成javadoc时会包含注解。
 * <p>
 * 使用自定义注解:
 * @<注解名>(<成员名1>=<成员值1>,<成员名1>=<成员值1>,…)
 *
 * ref:http://www.importnew.com/23564.html
 *     https://www.jianshu.com/p/0b1af95c1335
 */

@Target({ElementType.METHOD, ElementType.TYPE})  //定义作用域
@Retention(RetentionPolicy.RUNTIME) //定义生命周期
@Inherited //标示性的元注解，它允许子注解继承它
@Documented //生成javaDoc时会包含注解
public @interface DescriptionDemo { //使用@interface自定义注解，自动继承了java.lang.annotation.Annotation接口
    /**
     * 1.成员类型是受限制的，合法的类型包括基本的数据类型以及String，Class，Annotation,Enumeration等。
     * 2.如果注解只有一个成员，则成员名必须取名为value()，在使用时可以忽略成员名和赋值号（=）。
     * 3.注解类可以没有成员，没有成员的注解称为标识注解。
     */
    String desc();//成员变量（成员以无参无异常的方式声明）

    String name();

    int age() default 18;//（成员变量可以用default指定一个默认值的）


}
