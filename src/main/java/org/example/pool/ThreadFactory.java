package org.example.pool;

@FunctionalInterface
public interface ThreadFactory {

    Thread createThread(Runnable runnable);
}
