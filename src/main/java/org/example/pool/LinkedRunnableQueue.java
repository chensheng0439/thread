package org.example.pool;

import java.util.LinkedList;

public class LinkedRunnableQueue implements RunnableQueue {

    // 任务队列的最大容量，在构造时传入
    private final int limit;
    // 若任务队列的任务已经满了，则需要执行拒绝策略
    private final DenyPolicy denyPolicy;
    // 存放任务的队列
    private final LinkedList<Runnable> runnableLinkedList = new LinkedList<>();

    private final ThreadPool threadPool;

    public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool) {
        this.limit = limit;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }

    @Override
    public void offer(Runnable runnable) {
        synchronized (runnableLinkedList){
            if(runnableLinkedList.size()>= limit){
                denyPolicy.reject(runnable,threadPool);
            }else{
                runnableLinkedList.add(runnable);
                runnableLinkedList.notifyAll();
            }
        }
    }

    @Override
    public Runnable take() {
        synchronized (runnableLinkedList){
            while (runnableLinkedList.isEmpty()){
                try {
                    runnableLinkedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return runnableLinkedList.removeFirst();
    }

    @Override
    public int size() {
        synchronized (runnableLinkedList){
            return runnableLinkedList.size();
        }
    }
}
