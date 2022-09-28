package org.thread;

import javafx.beans.binding.ObjectExpression;

import static java.lang.Thread.sleep;

/**
 * @author tjp
 */
public class HelloThread {

    public static void main(String[] args) {

//        Window w1=new Window();
//        Window w2=new Window();
//        Window w3=new Window();
//
//        w1.start();
//        w2.start();
//        w3.start();
        WindowR wr=new WindowR();
        Thread t1=new Thread(wr);
        Thread t2=new Thread(wr);
        Thread t3=new Thread(wr);
        t1.start();
        t2.start();
        t3.start();
    }
}
class Window extends Thread{
    private static int ticket=100;
    @Override
    public void run() {
        Object obj=new Object();

        while (true) {
            synchronized (obj) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                ticket = getTicket();
                if (ticket > 0) {
                    System.out.println(getName() + ":" + ticket);
                    ticket -= 1;
                } else {
                    break;
                }
            }
        }
    }
}

class WindowR implements Runnable{
    private  int ticket=100;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + ":" + ticket);
                    ticket -= 1;
                } else {
                    break;
                }
            }
        }
    }
}