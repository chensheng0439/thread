package org.example.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {

    // 固定长度的线程池
   /* public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for(int i=0;i<10;i++){
            pool.submit(new MyThread());
        }
        pool.shutdown();
    }*/

    // 单线程线程池
    public static void main(String[] args) {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for(int i=0;i<100;i++){
            pool.submit(new MyThread());
        }
        pool.shutdown();
    }
}
