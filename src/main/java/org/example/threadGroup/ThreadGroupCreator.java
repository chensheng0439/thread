package org.example.threadGroup;

public class ThreadGroupCreator {

    public static void main(String[] args) {
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();

        ThreadGroup group1 = new ThreadGroup("group1");

        System.out.println(group1.getParent() == currentGroup);

        ThreadGroup group2 = new ThreadGroup(group1,"group2");

        System.out.println(group2.getParent() == group1);
    }
}
