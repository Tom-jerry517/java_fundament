package org.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author cy
 */
public class DynamicProxyTest {
    public static void main(String[] args) {
        Human proxyInstance = (Human) ProxyFactory.getProxyInstance(new SuperMan());
        proxyInstance.getBelief();
        System.out.println(proxyInstance);
    }
}

interface Human{
    void getBelief();
}

class SuperMan implements Human{
    @Override
    public void getBelief() {
        System.out.println("I can fly");
    }
}

class ProxyFactory{
    /**
     * 调用此方法，动态返回一个代理类的对象
     * @param obj 被代理类的对象
     * @return 代理类的对象
     */
    public static Object getProxyInstance(Object obj){
        MyInvocationHandler handler=new MyInvocationHandler();
        handler.bind(obj);
        //动态创建
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
    }
}
class MyInvocationHandler implements InvocationHandler{
    //被代理类对象
    private Object obj;

    public void bind(Object obj){
        this.obj=obj;
    }

    //当我们通过代理类的对象，调用此方法时，就会自动的调用如下的方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before.....");
        Object invoke = method.invoke(obj, args);
        System.out.println("after.....");
        fina();
        return invoke;
    }

    public static void fina(){
        System.out.println("finally");
    }
}