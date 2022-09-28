package org.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        AnimalInvocationHandler animalInvocationHandler = new AnimalInvocationHandler();
        animalInvocationHandler.bind(new Cat());
        Animal animal =(Animal) Proxy.newProxyInstance(Cat.class.getClassLoader(), Cat.class.getInterfaces(), animalInvocationHandler);
        animal.say();
        System.out.println(animal);
    }
}
interface Animal{
    void say();
}
class Cat implements Animal{
    @Override
    public void say() {
        System.out.println("I am cat!");
    }
}
class AnimalInvocationHandler implements InvocationHandler{
    private Animal animal;

    public void bind(Animal animal){
        this.animal=animal;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(animal, args);
        after(result);
        return result;
    }

    private void before() {
        System.out.println("is alive");
    }

    private void after(Object result){
        System.out.println(result+"is checked!");
    }
}