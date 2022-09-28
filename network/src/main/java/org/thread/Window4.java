package org.thread;

/**
 * @author cy
 */
public class Window4 extends Thread{
    private static int ticket=100;

    public void run(){
        while (true){
            show();
            if (ticket==0)
                break;
        }
    }

    private static synchronized void show(){
        if (ticket>0){
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+":"+ticket);
            ticket--;
        }
    }

    public static void main(String[] args) {
        Window4 t1=new Window4();
        Window4 t2=new Window4();
        Window4 t3=new Window4();

        t1.start();
        t2.start();
        t3.start();
    }
}
