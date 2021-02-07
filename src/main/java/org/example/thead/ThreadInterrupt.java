package org.example.thead;

import java.util.concurrent.TimeUnit;

public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Oh,I am be interrupted。。。。。。");
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(10);
        thread.interrupt();
    }
}
