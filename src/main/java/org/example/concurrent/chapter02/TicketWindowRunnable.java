package org.example.concurrent.chapter02;

public class TicketWindowRunnable implements Runnable{

    private int index = 1;

    private static final int MAX = 500;

    private static final Object MUTEX = new Object();

    @Override
    public void run() {
        synchronized(MUTEX){
            while(index <= 500){
                System.out.println(Thread.currentThread() + " 的号码是："+index++);
            }
        }
    }

    public static void main(String[] args) {
        TicketWindowRunnable task = new TicketWindowRunnable();
        Thread t1 = new Thread(task,"一号窗口");
        Thread t2 = new Thread(task,"二号窗口");
        Thread t3 = new Thread(task,"三号窗口");
        Thread t4 = new Thread(task,"四号窗口");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
