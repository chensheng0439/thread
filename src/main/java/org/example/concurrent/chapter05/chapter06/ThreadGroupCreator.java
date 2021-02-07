package org.example.concurrent.chapter05.chapter06;

public class ThreadGroupCreator {

    public static void main(String[] args) {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup group1 = new ThreadGroup("Group1");
        System.out.println(group1.getParent() == threadGroup);

        ThreadGroup group2 = new ThreadGroup(group1,"Group1");

        System.out.println(group2.getParent() == group1);
    }
}
