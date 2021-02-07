package org.example.thead;

import java.util.concurrent.TimeUnit;

public class ThreadIsInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            /*while(true){

            }*/
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                System.out.println("当前线程被中断了");
            }
        });

        thread.start();
        TimeUnit.MILLISECONDS.sleep(2);
        System.out.printf("Thread is Interrupted ? %s\n", thread.isInterrupted());
        thread.interrupt();
        System.out.printf("Thread is Interrupted ? %s\n", thread.isInterrupted());
    }
}
