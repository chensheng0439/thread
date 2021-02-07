package org.example.thead;

import java.util.concurrent.TimeUnit;

public class ThreadSleep {

    public static void main(String[] args) {
        new Thread(()->{
            long startTime = System.currentTimeMillis();
            sleep(2L);
            long endTime = System.currentTimeMillis();
            System.out.println(String.format("Total spend %d ms",(endTime - startTime)));
        }).start();

        long startTime = System.currentTimeMillis();
        sleep(3L);
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Main Thread Total spend %d ms",(endTime - startTime)));
    }

    private static void sleep(long ms){
        try{
            // Thread.sleep(ms);
            // 使用TimeUtil 代替sleep
            TimeUnit.SECONDS.sleep(ms);
        }catch (Exception e){

        }

    }
}
