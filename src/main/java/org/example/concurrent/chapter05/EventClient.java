package org.example.concurrent.chapter05;

import java.util.concurrent.TimeUnit;

public class EventClient {

    public static void main(String[] args) {
        final EventQueue eventQueue = new EventQueue();
        new Thread(()->{
            for(;;){
                eventQueue.offer(new EventQueue.Event());
            }
        },"Producer1").start();

        new Thread(()->{
            for(;;){
                eventQueue.offer(new EventQueue.Event());
            }
        },"Producer2").start();

        new Thread(()->{
            for(;;){
                eventQueue.offer(new EventQueue.Event());
            }
        },"Producer3").start();

        new Thread(()->{
            for(;;){
                eventQueue.offer(new EventQueue.Event());
            }
        },"Producer4").start();

        new Thread(()->{
            for(;;){
                eventQueue.task();
                try{
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Consumer1").start();

        new Thread(()->{
            for(;;){
                eventQueue.task();
                try{
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Consumer2").start();

        new Thread(()->{
            for(;;){
                eventQueue.task();
                try{
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Consumer3").start();


        new Thread(()->{
            for(;;){
                eventQueue.task();
                try{
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Consumer4").start();


    }
}
