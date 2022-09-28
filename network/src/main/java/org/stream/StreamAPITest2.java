package org.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream中间操作
 * @author cy
 */
public class StreamAPITest2 {
    @Test
    public void test1(){
       List<Integer> list=new ArrayList<>();
       list.add(1);
       list.add(1234);
       list.add(1234);
       list.add(111);
       list.add(111);
       list.add(12);
       list.add(10);
        Stream<Integer> stream = list.stream();

        stream.distinct().forEach(System.out::println);
        list.forEach(System.out::println);
//        stream.forEach(System.out::println);
    }

    @Test
    public void test2(){
        List<Integer> list= Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Integer sum = list.stream().reduce(0, Integer::sum);
        System.out.println(sum);
    }
     @Test
    public void test3(){
        List<Integer> list= Arrays.asList(1,2,3,4,5,6,7,8,9,10);
         List<Integer> collect = list.stream().filter(integer -> integer > 5).collect(Collectors.toList());
         collect.forEach(System.out::println);
    }




}
