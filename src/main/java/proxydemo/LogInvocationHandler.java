
package proxydemo;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;


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


/**
 * JDK动态代理的实现
 */
//代理类
@Slf4j
public class LogInvocationHandler implements InvocationHandler {
    //目标类，在LogInvocationHandler实例化的时候传入进来
    private Hello hello;

    public LogInvocationHandler(Hello hello) {
        this.hello = hello;
    }

    //关键方法
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("sayHello".equals(method.getName())) {
            log.info("You said: " + Arrays.toString(args));
        }

        log.info("------实现前置通知------");
        //代理对象调用转发给目标对象，这里相当于调用了HelloImp的sayHello方法
        Object obj = method.invoke(hello, args);
        log.info("------实现后置通知------");
        log.info("------实现其他附加功能------");

        return obj;
    }

}

@Slf4j
class LogInvocationHandlerTest {
    public static void main(String[] args) {


        // 2. 然后在需要使用Hello的时候，通过JDK动态代理获取Hello的代理对象。
        Hello hello = (Hello) Proxy.newProxyInstance(Hello.class.getClassLoader(), // 1. 类加载器
                new Class<?>[]{Hello.class}, // 2. 代理需要实现的接口，可以有多个
                new LogInvocationHandler(new HelloImp()));// 3. 方法调用的实际处理者
        log.info(hello.sayHello("I love you!"));

    }
}
