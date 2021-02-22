package org.example.threadGroup;

import java.util.concurrent.TimeUnit;

public class ThreadGroupEnumrateGroups {

    public static void main(String[] args) throws InterruptedException {

        ThreadGroup group1 = new ThreadGroup("MyGroup1");
        ThreadGroup group2 = new ThreadGroup(group1,"MyGroup2");

        TimeUnit.MILLISECONDS.sleep(2);

        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();

        ThreadGroup[] list = new ThreadGroup[mainGroup.activeGroupCount()];

        int recurseSize = mainGroup.enumerate(list);

        System.out.println(recurseSize);

        recurseSize = mainGroup.enumerate(list,false);
        System.out.println(recurseSize);
    }
}
