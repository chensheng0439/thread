package org.example.pool;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BasicThreadPool extends Thread implements ThreadPool{

    // 初始化线程数量
    private final int initSize;
    // 线程池最大线程数量
    private final int maxSize;
    // 线程池核心线程数量
    private final int coreSize;

    //当前活跃的线程数量
    private int activeCount;
    // 创建线程所需的工厂
    private final ThreadFactory threadFactory;
    // 任务队列
    private final RunnableQueue runnableQueue;
    // 线程池是否已经被shutdown
    private volatile boolean isShutdown = false;

    private final Queue<ThreadTask> threadQueue = new ArrayDeque<>();

    private final static DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();

    private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();

    private final long keepAliveTime;

    private final TimeUnit timeUnit;

    public BasicThreadPool(int initSize,int maxSize,int coreSize,int queueSize){
        this(initSize,maxSize,coreSize,DEFAULT_THREAD_FACTORY,queueSize,DEFAULT_DENY_POLICY,10,TimeUnit.SECONDS);
    }
    public BasicThreadPool(int initSize,int maxSize,int coreSize,ThreadFactory threadFactory,int queueSize,
                           DenyPolicy denyPolicy,long keepAliveTime,TimeUnit timeUnit){
        this.initSize = initSize;
        this.maxSize = maxSize;
        this.coreSize = coreSize;
        this.threadFactory = threadFactory;
        this.runnableQueue = new LinkedRunnableQueue(queueSize,denyPolicy,this);
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.init();
    }
    private void init(){
        start();
        for(int i = 0;i<initSize;i++){
            newThread();
        }
    }
    private void newThread(){
        InternalTask internalTask = new InternalTask(runnableQueue);
        Thread thread = this.threadFactory.createThread(internalTask);
        ThreadTask threadTask = new ThreadTask(thread,internalTask);
        threadQueue.offer(threadTask);
        this.activeCount++;
        thread.start();
    }
    public static class ThreadTask{
        Thread thread;
        InternalTask internalTask;

        public ThreadTask(Thread thread, InternalTask internalTask) {
            this.thread = thread;
            this.internalTask = internalTask;
        }
    }

    public static class DefaultThreadFactory implements ThreadFactory{
        private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);

        private static final ThreadGroup group = new ThreadGroup("MyThreadGroup-" + GROUP_COUNTER.getAndDecrement());

        private static final AtomicInteger COUNT = new AtomicInteger(0);

        @Override
        public Thread createThread(Runnable runnable) {
            return new Thread(group,runnable,"thread-pool-" + COUNT.getAndDecrement());
        }
    }
    private void removeThread(){
        ThreadTask threadTask = threadQueue.remove();
        threadTask.internalTask.stop();
        this.activeCount--;
    }
    @Override
    public void execute(Runnable runnable) {
        if(this.isShutdown){
            throw new IllegalStateException("The thread pool is destroy");
        }
        this.runnableQueue.offer(runnable);
    }

    @Override
    public void shutdown() {
        synchronized (this){
            if(isShutdown){
                return;
            }
            isShutdown = true;
            threadQueue.forEach(threadTask -> {
                threadTask.internalTask.stop();
                threadTask.thread.isInterrupted();
            });
            this.interrupt();
        }
    }

    @Override
    public int getInitSize() {
        if(isShutdown){
            throw new IllegalStateException("The thread pool is destroy");
        }
        return initSize;
    }

    @Override
    public int getMaxSize() {
        if(isShutdown){
            throw new IllegalStateException("The thread pool is destroy");
        }
        return maxSize;
    }

    @Override
    public int getCoreSize() {
        if(isShutdown){
            throw new IllegalStateException("The thread pool is destroy");
        }
        return coreSize;
    }

    @Override
    public int getQueueSize() {
        if(isShutdown){
            throw new IllegalStateException("The thread pool is destroy");
        }
        return threadQueue.size();
    }

    @Override
    public int getActiveCount() {
        if(isShutdown){
            throw new IllegalStateException("The thread pool is destroy");
        }
        return activeCount;
    }

    @Override
    public boolean isShutDown() {
        if(isShutdown){
            throw new IllegalStateException("The thread pool is destroy");
        }
        return isShutdown;
    }

    @Override
    public void run() {
        while(!isShutdown && !isInterrupted()){
            try{
                timeUnit.sleep(keepAliveTime);
            } catch (InterruptedException e) {
                isShutdown = true;
                break;
            }
            synchronized (this){
                if(isShutdown){
                    break;
                }
                if(runnableQueue.size() > 0 && activeCount < coreSize){
                    for(int i = initSize; i < coreSize; i++){
                        newThread();
                    }
                    continue;
                }
                if(runnableQueue.size() > 0 && activeCount < maxSize){
                    newThread();
                }

                if(runnableQueue.size() == 0 && activeCount > coreSize){
                    for(int i = coreSize;i < activeCount; i++){
                        removeThread();
                    }
                }
            }
        }
    }
}
