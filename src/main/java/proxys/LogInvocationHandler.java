package proxys;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import org.apache.log4j.Logger;

// 接口
interface Hello {
    String sayHello(String str);
}

// 实现
class HelloImp implements Hello {

    public String sayHello(String str) {
        return "HelloImp: " + str;
    }
}

public class LogInvocationHandler implements InvocationHandler {
    private static Logger logger = Logger.getLogger(LogInvocationHandler.class);

    private Hello hello;

    public LogInvocationHandler(Hello hello) {
        this.hello = hello;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("sayHello".equals(method.getName())) {
            logger.info("You said: " + Arrays.toString(args));
        }
        return method.invoke(hello, args);
    }

}

//class LogInvocationHandlerTest {
//    public static void main(String[] args) {
//
//        // 2. 然后在需要使用Hello的时候，通过JDK动态代理获取Hello的代理对象。
//        Hello hello = (Hello) Proxy.newProxyInstance(
//                getClass().getClassLoader(), // 1. 类加载器
//                new Class<?>[]{Hello.class}, // 2. 代理需要实现的接口，可以有多个
//                new LogInvocationHandler(new HelloImp()));// 3. 方法调用的实际处理者
//        System.out.println(hello.sayHello("I love you!"));
//
//    }
//}
