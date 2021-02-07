package org.example.thead;

import java.util.concurrent.TimeUnit;

public class TryConcurrency {

    public static void main(String[] args) {
        browseNews();
        //enjoyMusic();
        new Thread(TryConcurrency::enjoyMusic).start();
    }

    private static void browseNews(){
        new Thread(()->{
            Thread.currentThread().setName("浏览线程");
            for(;;){
                System.out.println("Uh-huh,the good news.====="+Thread.currentThread().getName());
                sleep(1);
            }
        }).start();

    }

    private static void enjoyMusic(){
        Thread.currentThread().setName("音乐线程");
        for(;;){
            System.out.println("Uh-huh,the nice music.======"+Thread.currentThread().getName());
            sleep(1);
        }
    }


    private static void sleep(int seconds){
        try{
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
