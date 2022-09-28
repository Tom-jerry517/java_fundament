package org.thread;

/**
 * @author cy
 * callable shijianzhiwaidegushi*/
public class JoinTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadA t1=new ThreadA();
        ThreadA t2=new ThreadA();

        t1.start();
        t2.start();
    }
}
class ThreadA extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(getName()+"--"+i);
        }
    }
}
