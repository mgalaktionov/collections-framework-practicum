package com.example.stream;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class StreamTest {
    
    @Test
    void sequentialStream_test() {
        var list = new ArrayList<Runnable>();
        Runnable runnable = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        };
        
        list.add(runnable);
        list.add(runnable);
        list.add(runnable);
        
        Runnable sequential = () -> list.stream().forEach(r -> r.run());
        
        logExecutionTime(sequential);
    }
    
    @Test
    void parallelStream_test() {
        var list = new ArrayList<Runnable>();
        Runnable runnable = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        };
        
        list.add(runnable);
        list.add(runnable);
        list.add(runnable);
        
        Runnable parallel = () -> list.parallelStream().forEach(r -> r.run());
        
        logExecutionTime(parallel);
    }
    
    @Test
    void parallel_necessity_test() {
        System.out.println("складываем числа последовательно");
        logExecutionTime(() -> System.out.println(
            IntStream.rangeClosed(1, 1_000_000).reduce(0, Integer::sum)
        ));
        
        System.out.println("складываем числа параллельно");
        logExecutionTime(() -> System.out.println(
            IntStream.rangeClosed(1, 1_000_000).parallel().reduce(0, Integer::sum)
        ));
    }
    
    @Test
    void splittingCost_test(){
        var arrayListOfNumbers = new ArrayList<Integer>();
        var linkedListOfNumbers = new LinkedList<Integer>();
    
        IntStream.rangeClosed(1, 1_000_000).forEach(i -> {
            arrayListOfNumbers.add(i);
            linkedListOfNumbers.add(i);
        });
    
        System.out.println("Последовательно считаем сумму элементов в arrayList");
        logExecutionTime(() ->arrayListOfNumbers.stream().reduce(0, Integer::sum));
    
        System.out.println("Параллельно считаем сумму элементов в arrayList");
        logExecutionTime(() ->arrayListOfNumbers.parallelStream().reduce(0, Integer::sum));
    
        System.out.println("Последовательно считаем сумму элементов в linkedList");
        logExecutionTime(() ->linkedListOfNumbers.stream().reduce(0, Integer::sum));
        
        System.out.println("Параллельно считаем сумму элементов в linkedList");
        logExecutionTime(() ->linkedListOfNumbers.parallelStream().reduce(0, Integer::sum));
    }
    
    @Test
    void mergingCost_test() {
        var arrayListOfNumbers = new ArrayList<Integer>();
        var linkedListOfNumbers = new LinkedList<Integer>();
    
        IntStream.rangeClosed(1, 1_000_000).forEach(i -> {
            arrayListOfNumbers.add(i);
            linkedListOfNumbers.add(i);
        });
    
        System.out.println("Последовательно собираем элементы в сет");
        logExecutionTime(() ->arrayListOfNumbers.stream().collect(Collectors.toSet()));
    
        System.out.println("Параллельно собираем элементы в сет");
        logExecutionTime(() ->arrayListOfNumbers.stream().parallel().collect(Collectors.toSet()));
    }
    
    void logExecutionTime(Runnable r) {
        var start = System.currentTimeMillis();
        r.run();
        var end = System.currentTimeMillis();
        System.out.println(String.format("выполнение заняло %s мс", end - start));
    }
}
