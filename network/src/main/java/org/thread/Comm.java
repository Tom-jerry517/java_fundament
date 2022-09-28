package org.thread;

/**
 * 线程通信 wait notify
 * 交替打印
 * @author tjp
 */
public class Comm implements Runnable {
    private int number=1;

    @Override
    public void run() {
        int loop = 100;
        for (int i = 0; i < loop; i++) {
            synchronized (this){
                notify();
                if (number<=100) {
                    System.out.println(Thread.currentThread().getName()+":"+number++);
                    try {
                        wait();
                        System.out.println(Thread.currentThread().getName()+"wait");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

class Test{
    public static void main(String[] args) {
        Comm comm=new Comm();
        Thread t1=new Thread(comm);
        Thread t2=new Thread(comm);

        t1.setName("线程1");
        t2.setName("线程2");
        t1.start();
        t2.start();

    }
}
