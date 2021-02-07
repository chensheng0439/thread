package org.example.concurrent.chapter01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

public class FightQueryExample {

    private static List<String> fightCompany = Arrays.asList("CSA","CEA","HNA");
    public static void main(String[] args) {
        List<String> results = search("SH","BJ");
        System.out.println("===================result=====================");
        results.forEach(System.out::println);

    }

    public static List<String> search(String origin,String dest ){
        final List<String> result = new ArrayList<>();
        List<FightQueryTask> tasks = fightCompany.stream().map(f->createSearchTask(f,origin,dest)).collect(toList());
        tasks.forEach(Thread::start);
        tasks.forEach(task->{
            try {
                task.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        tasks.stream().map(FightQuery::get).forEach(result::addAll);
        return result;
    }

    private static FightQueryTask createSearchTask(String fight,String origin,String dest){
        return new FightQueryTask(fight,origin,dest);
    }
}
