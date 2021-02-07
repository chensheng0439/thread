package org.example.concurrent.chapter01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class FightQueryTask extends Thread implements FightQuery{

    private final String origin;

    private final String description;

    private final List<String> flightList = new ArrayList<>();

    public FightQueryTask(String airline,String origin,String description){
        super("[" + airline + "]");
        this.origin = origin;
        this.description = description;
    }



    @Override
    public void run() {
        System.out.printf("%s-query from %s to %s \n",getName(),origin,description);
        int randomVal = ThreadLocalRandom.current().nextInt(10);
        try{
            TimeUnit.SECONDS.sleep(randomVal);
            this.flightList.add(getName()+"-"+randomVal);
            System.out.printf("The Fight: %s list query successful\n",getName());
        }catch (InterruptedException e){
            System.out.println("中断异常......");
        }
    }

    @Override
    public List<String> get() {
        return this.flightList;
    }
}
