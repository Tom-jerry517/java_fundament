package org.io;

import java.util.Comparator;

/**
 * @author tjp
 */
public class ComparatorTest {

    public static void main(String[] args) {
        Son s1=new Son("sajk");
        Son s2=new Son("sajk");
        System.out.println(s1.compare(s1,s2));
    }


}
class Base {
    String msg;
    public Base(String msg){
        this.msg=msg;
    }
}
class Son extends Base implements Comparator<Base>{

    public Son(String msg) {
        super(msg);
    }

    @Override
    public int compare(Base o1, Base o2) {
        return o1.msg.compareTo(o2.msg);
    }
}