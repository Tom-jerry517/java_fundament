package org.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author tjp
 */
public class LambdaTest2 {
    static void h(double money, Consumer<Double> con){
        con.accept(money);
    }

    static void test(List<String> strings, Predicate<String> predicate){
        strings.forEach(str->{
            if (predicate.test(str)) {
                System.out.println(str);
            }
        });
    }
    @Test
    public void test1(){
        List<String> list=new ArrayList<>();
        list.add("ahsjkas");
        list.add("akas");
        list.add("jkasasdasdasd");
        list.add("ahs");
        test(list,s -> s.length()>=5);
    }

}
