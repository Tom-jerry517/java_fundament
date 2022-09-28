package org.thread;

import java.util.Random;

class Clerk{

    private int productCount=0;

    public synchronized void produceProduct() {
        if (productCount<20){
            productCount++;
            System.out.println(Thread.currentThread().getName()+":开始生产第"+productCount+"个产品");
            notify();
        }else {
            try {
                wait();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public synchronized void consumeProduct() {
        if (productCount>0){
            System.out.println(Thread.currentThread().getName()+":开始消费第"+productCount+"个产品");
            productCount--;

            notify();
        }else {
            try {
                wait();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
class Productor extends Thread{
    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(getName()+":开始生产产品");
        while (true){
            try {
                Thread.sleep(new Random().nextInt(2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            clerk.produceProduct();
        }
    }
}
class Consumer extends Thread{
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(getName()+":开始消费产品");
        while (true){
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            clerk.consumeProduct();
        }
    }
}

/**
 * 生产者/消费者问题
 * @author cy
 */
public class ProductTest {
    public static void main(String[] args) {
        Clerk clerk=new Clerk();
        Productor p1=new Productor(clerk);
        p1.setName("生产者1");

        Consumer c1=new Consumer(clerk);
        c1.setName("消费者1");

        p1.start();
        c1.start();
    }
}