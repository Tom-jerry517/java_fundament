package org.thread;

/**
 * @author tjp
 * 银行存钱
 */
public class Account {
    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }
    public void addBalance(double balance){
        this.balance+=balance;
    }

    public double getBalance(){
        return balance;
    }
}
class Consume implements Runnable{

    private Account account;

    public Consume(Account account) {
        this.account = account;
    }
    @Override
    public  void run() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (Consume.class){
            account.addBalance(1000);
            System.out.println(Thread.currentThread().getName()+"存钱----余额为："+account.getBalance());
            }
        }
    }
}

class Main{
    public static void main(String[] args) {
        Account account=new Account(0);
        Consume c1=new Consume(account);
//        Consume c2=new Consume(account);

        new Thread(c1).start();
        new Thread(c1).start();

    }
}
