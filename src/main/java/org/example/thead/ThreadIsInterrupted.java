package org.example.thead;

import java.util.concurrent.TimeUnit;

public class ThreadIsInterrupted {

    public static void main(String[] args) throws InterruptedException {
        /*Thread thread = new Thread(()->{
            while (true){
                try{
                    TimeUnit.SECONDS.sleep(10);
                }catch (InterruptedException e){
                    System.out.printf("I am be interrupted? %s\n");
                }
            }
        });*/
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                        TimeUnit.MINUTES.sleep(1);
                    }catch (InterruptedException e){
                        System.out.printf("I am be interrupted? %s\n");
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        TimeUnit.MILLISECONDS.sleep(30);
        System.out.printf("I am be interrupted? %s\n",thread.isInterrupted());
        thread.interrupt();
        TimeUnit.MILLISECONDS.sleep(2);
        System.out.printf("I am be interrupted? %s\n",thread.isInterrupted());
    }
}
