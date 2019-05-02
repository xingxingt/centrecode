package reflectdemo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectPrivateFieldDemo {


    public static void main(String[] args) throws Exception {

        //直接创建对象
        Person person = new Person();

        Class<?> personType = person.getClass();

        //访问私有方法
        //getDeclaredMethod可以获取到所有方法，而getMethod只能获取public
        Method method = personType.getDeclaredMethod("say", String.class);

        //压制Java对访问修饰符的检查
        method.setAccessible(true);

        //调用方法;person为所在对象
        method.invoke(person, "Hello World !");

        //访问私有属性
        Field field = personType.getDeclaredField("name");
        System.out.println(field.getName());

        //todo 当反射对象的accessible标志设为true时，则表示反射的对象在使用时应该取消Java语言访问检查。
        //todo 反之则检查。由于JDK的安全检查耗时较多，所以通过setAccessible(true)的方式关闭安全检查来提升反射速度。
        field.setAccessible(true);

        //为属性设置值;person为所在对象
        field.set(person, "WalkingDog");

        System.out.println("The Value Of The Field is : " + person.getName());

    }
}

//JavaBean
class Person {
    private String name;

    //每个JavaBean都应该实现无参构造方法
    public Person() {
    }

    public String getName() {
        return name;
    }

    private void say(String message) {
        System.out.println("You want to say : " + message);
    }
}
