package org.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 测试<b>方法引用</b>
 * @author cy
 */
public class LambdaTest3 {

    @Test
    public void test1(){
        Consumer<String> con1= str -> System.out.println(str);
        con1.accept("hello");
        Consumer<String> con2= System.out::println;
        con2.accept("hello");
    }

    @Test
    public void test2(){
        class T{
            final int [] cache={1,2,3,4,5,6,7,8,9};
            int flag=0;
            int getFlag(){
                if (flag>=9){
                    flag=0;
                }
                return cache[flag++];
            }
        }
        T t = new T();
        Supplier<Integer> supplier=t::getFlag;
        for (int i = 0; i < 10; i++) {
            System.out.println(supplier.get());
        }
    }

    @Test
    public void test3(){
        //Comparator 的 int compare(T t1,T t2)
        //String     的 t1.compareTo(t2)
        Comparator<String> comparator=(s1,s2)-> s1.compareTo(s2);
        System.out.println(comparator.compare("ab", "ass"));
        //参数类型不一致，也可以 方法引用   可以看作隐式参数是方法的调用者
        Comparator<String> comparator1= String :: compareTo;
        System.out.println(comparator1.compare("ab", "ass"));//comparator1.compare("ab", "ass") --> "ab".compareTo("ass")
    }

}
