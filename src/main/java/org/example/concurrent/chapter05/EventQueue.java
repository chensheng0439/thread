package org.example.concurrent.chapter05;

import java.util.LinkedList;

public class EventQueue {

    private final int max;

    static class Event{

    }

    private final LinkedList<Event> eventQueue = new LinkedList<>();

    private final static int DEFAULT_MAX_EVENT = 10;

    public EventQueue() {
        this(DEFAULT_MAX_EVENT);
    }

    public EventQueue(int max) {
        this.max = max;
    }

    public void offer(Event event){
        synchronized(eventQueue){
            while (eventQueue.size() >= max){
                try{
                 console("the queue is full "+eventQueue.size());
                 eventQueue.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            console("the new event is submitted!");
            eventQueue.add(event);
            //eventQueue.notify();
            eventQueue.notifyAll();
        }
    }

    public Event task(){
        synchronized(eventQueue){
            while (eventQueue.isEmpty()){
                try{
                    console("the queue is empty");
                    eventQueue.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            Event event = eventQueue.getFirst();
            //eventQueue.notify();
            eventQueue.notifyAll();
            console("the event" + event + " is handled.");
            return event;
        }
    }

    private void console(String message){
        System.out.printf("%s:%s\n",Thread.currentThread().getName(),message);
    }
}
