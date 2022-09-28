package org.annotation;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 测试注解
 * @author tjp
 */
public class AnnotationTest {
    static class Student{
        @Label("姓名")
        String name;
        @Label("出生日期")
        @Format(pattern = "yyyy/MM/dd")
        Date born;
        @Label("分数")
        Double score;

        public Student(String name, Date born, Double score) {
            this.name = name;
            this.born = born;
            this.score = score;
        }
    }
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy----MM");
        Student zhang=new Student("张三",sdf.parse("1900----12"),80.9d);
        System.out.println(SimpleFormatter.format(zhang));
    }
}
class SimpleFormatter{
    public static String format(Object obj){
        try{
            Class<?> cls=obj.getClass();
            StringBuilder sb=new StringBuilder();
            for (Field f:cls.getDeclaredFields()){
                if (!f.isAccessible()){
                    f.setAccessible(true);
                }
                Label label=f.getAnnotation(Label.class);
                String name=label!=null?label.value():f.getName();
                Object value=f.get(obj);
                if (value!=null&&f.getType()== Date.class){
                    value=formatDate(f,value);
                }
                sb.append(name).append(":").append(value).append("\n");
            }
            return sb.toString();
        }catch (IllegalAccessException e){
            throw new RuntimeException();
        }
    }

    public static Object formatDate(Field f,Object value){
        Format format=f.getAnnotation(Format.class);
        if (format!=null){
            SimpleDateFormat sdf=new SimpleDateFormat(format.pattern());
            sdf.setTimeZone(TimeZone.getTimeZone(format.timezone()));
            return sdf.format(value);
        }
        return value;
    }
}
