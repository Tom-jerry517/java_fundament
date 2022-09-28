package org.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream创建操作
 * @author cy
 */
public class StreamAPITest {
    @Test
    public void test1(){
        List<String> list=new ArrayList<>();
        list.add("a");
        list.add("aa");
        list.add("aaa");
        list.add("aaaa");
        //Collection 接口中的 default Stream<E> stream()
        Stream<String> stream = list.stream();
        System.out.println(stream);
        //Collection 接口中的 default Stream<E> parallelStream() 并行流
        Stream<String> stringStream = list.parallelStream();
        System.out.println(stringStream);

    }

    @Test
    public void test2(){
        //迭代
        Stream.iterate(0,t->t+2).limit(10).forEach(System.out::println);

        //生成
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }


}
