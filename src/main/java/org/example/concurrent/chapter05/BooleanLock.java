package org.example.concurrent.chapter05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.currentThread;

public class BooleanLock implements Lock{

    // 代表当前拥有锁的线程
    private Thread currentThread;
    // 代表锁的状态。false:锁没有被线程占用，true:锁已经被占用
    private boolean locked = false;
    // 哪些线程进去阻塞状态
    private final List<Thread> blockedList = new ArrayList<>();

    @Override
    public void lock() throws InterruptedException {
        synchronized(this){
            while(locked){
                final Thread thread = currentThread();
                try {
                    if(!blockedList.contains(thread)){
                        blockedList.add(currentThread());
                    }
                    this.wait();
                } catch (InterruptedException e) {
                    // 中断之后，直接移除
                    blockedList.remove(thread);
                    throw e;
                }
            }
            blockedList.remove(currentThread());
            this.locked = true;
            this.currentThread = currentThread();
        }

    }

    @Override
    public void lock(long mills) throws InterruptedException, TimeoutException {
        synchronized (this){
            if(mills <= 0){
                this.lock();
            }else{
                long remainingMills = mills;
                long endMills = currentTimeMillis()+ remainingMills;
                while (locked){
                    if(remainingMills <= 0){
                        throw new TimeoutException("can not get the lock during " + mills);
                    }
                    if(!blockedList.contains(currentThread())){
                        blockedList.add(currentThread());
                    }
                    this.wait(remainingMills);
                    remainingMills = endMills - currentTimeMillis();
                }
                blockedList.remove(currentThread());
                this.locked = true;
                this.currentThread = currentThread();
            }
        }
    }

    @Override
    public void unlock() {

        synchronized(this){
            if(currentThread == currentThread()){
                this.locked = false;
                this.notifyAll();
            }
        }

    }


    @Override
    public List<Thread> getBlockedThreads() {
        // 获取列表，但是不能对列表进行更新操作
        return Collections.unmodifiableList(this.blockedList);
    }
}
