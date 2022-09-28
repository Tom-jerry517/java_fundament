package org.lambda;

import org.proxy.Test;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaTest {

    public static void main(String[] args) {
        //语法格式1： 无参，无返回值
        Runnable r1 = () -> System.out.println("hello");
        r1.run(); // hello
        //语法格式2： 一个参数，无返回值
        Consumer<String> con1 = (String str) -> {
            System.out.println(str);
        };
        con1.accept("test"); // test
        //语法格式3： 数据类型可以省略，因为可以由编译器推断，称为“类型推断”
        Consumer<String> con2 = (str) -> {
            System.out.println(str);
        };
        //语法格式4： 若只有一个参数，小括号可以省略
        Consumer<String> con3 = str -> {
            System.out.println(str);
        };
        //语法格式5： 需要两个以上的参数，多条执行语句，并且可以有返回值
        Comparator<Integer> com1= ((o1, o2) -> {
            System.out.println(o1);
            System.out.println(o2);
            return o1.compareTo(o2);
        });
        //语法格式6： lambda体只有一条语句，return与大括号若有，都可以省略
        Comparator<Integer> com2= (o1, o2) -> o1.compareTo(o2);
    }
}
