package org.thread;

/**
 * 线程安全的懒汉式单例模式
 * @author tjp
 */
public class SingleTonTest {
    public static void main(String[] args) {
        Bank b1=Bank.getInstance();
        Bank b2=Bank.getInstance();
        Bank b3=Bank.getInstance();
        System.out.println(b1==b2);
        System.out.println(b1==b3);
    }

}
class Bank{
    private Bank(){}
    private static Bank instance=null;
    public static Bank getInstance(){
        if (instance==null){
            synchronized (Bank.class){
                if (instance==null){
                    instance=new Bank();
                }
            }
        }
        return instance;
    }
}