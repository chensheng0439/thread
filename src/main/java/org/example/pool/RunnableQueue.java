package org.example.pool;

public interface RunnableQueue {

    // 当有新的任务进来时会首先会offer到队列中
    void offer(Runnable runnable);

    // 工作线程通过take方式获取Runnable
    Runnable take();

    // 获取任务队列中任务的数量
    int size();
}
