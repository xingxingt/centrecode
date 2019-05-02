package proxydemo;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * CGLIB动态代理
 * CGLIB通过继承和引用的方式进行代理，无论目标对象有没有实现接口都可以代理，但是无法处理final的情况。
 * <p>
 * Cglib是一个优秀的动态代理框架，它的底层使用ASM在内存中动态的生成被代理类的子类，
 * 使用CGLIB即使代理类没有实现任何接口也可以实现动态代理功能。CGLIB具有简单易用，它的运行速度要远远快于JDK的Proxy动态代理：
 * <p>
 * <p>
 * cglib3.0+会出现Unable to load cache item异常，改为2.2版本正常
 */
// 1. 首先实现一个MethodInterceptor，方法调用会被转发到该类的intercept()方法。主要的方法拦截类
public class MyMethodInterceptor implements MethodInterceptor {


    /**
     * @param obj    代理对像
     * @param method 拦截的方法
     * @param args   方法的参数
     * @param proxy  代理对象
     * @return
     * @throws Throwable
     */
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("You said: " + Arrays.toString(args));
        return proxy.invokeSuper(obj, args);
    }


    public static void main(String[] args) {

        /**
         * 通过CGLIB的Enhancer来指定要代理的目标对象、实际处理代理逻辑的对象，
         * 最终通过调用create()方法得到代理对象，对这个对象所有非final方法的调用
         * 都会转发给MethodInterceptor.intercept()方法，在intercept()方法里我们可以加入任何逻辑，
         * 比如修改方法参数，加入日志功能、安全检查功能等；通过调用MethodProxy.invokeSuper()方法，
         * 我们将调用转发给原始对象，具体到本例，就是HelloConcrete的具体方法。
         * CGLIG中MethodInterceptor的作用跟JDK代理中的InvocationHandler很类似，都是方法调用的中转站
         */
        // 2. 然后在需要使用HelloConcrete的时候，通过CGLIB动态代理获取代理对象。
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloConcrete.class);
        enhancer.setCallback(new MyMethodInterceptor());

        HelloConcrete hello = (HelloConcrete) enhancer.create();
        System.out.println(hello.sayHello("I love you!"));


    }
}



