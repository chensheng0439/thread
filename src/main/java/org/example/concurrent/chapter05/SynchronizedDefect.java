package org.example.concurrent.chapter05;

import java.util.concurrent.TimeUnit;

public class SynchronizedDefect {

    public synchronized void syncMethod(){
        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedDefect synchronizedDefect = new SynchronizedDefect();
        Thread t1 = new Thread(synchronizedDefect::syncMethod,"T1");
        t1.start();
        TimeUnit.MILLISECONDS.sleep(2);
        Thread t2 = new Thread(synchronizedDefect::syncMethod,"T2");
        t2.start();
    }
}
