package org.example.thead;

import java.util.stream.IntStream;

public class MainTest {

    public static void main(String[] args) {
        IntStream.range(0,10).boxed().map(i->new Thread(()->{
            System.out.println(Thread.currentThread().getName());
        })).forEach(Thread::start);
    }
}
