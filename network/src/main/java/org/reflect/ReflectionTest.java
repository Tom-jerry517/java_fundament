package org.reflect;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Properties;

/**
 * @author cy
 */
public class ReflectionTest {

    public static void main(String[] args) throws Exception {
        Class<String> clazz = String.class;
        Method[] methods = clazz.getMethods();
        for (Method m:methods){
            Annotation[] annotations = m.getAnnotations();

            if (annotations!=null){
            for (Annotation a:annotations){
                System.out.println(a);
                System.out.println("---");
                }
            }
        }
    }
}
