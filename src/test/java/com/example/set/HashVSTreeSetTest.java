package com.example.set;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HashVSTreeSetTest {
    
    @Test
    void hashSet_order_test() {
        var hashSet = new HashSet<Integer>();
        hashSet.add(4);
        hashSet.add(3);
        hashSet.add(2);
        hashSet.add(1);
        
        System.out.println(hashSet);
        assertThat(hashSet, containsInAnyOrder(1, 2, 3, 4));
    }
    
    @Test
    void treeSet_order_test() {
        var treeSet = new TreeSet<Integer>();
        treeSet.add(4);
        treeSet.add(3);
        treeSet.add(2);
        treeSet.add(1);
        
        System.out.println(treeSet);
        assertThat(treeSet, contains(1, 2, 3, 4));
    }
    
    @Test
    void treeSet_comparator_order_test() {
        var treeSet = new TreeSet<Human>(Comparator.comparing(Human::age));
        treeSet.add(new Human(23, "Миша"));
        treeSet.add(new Human(22, "Вова"));
        treeSet.add(new Human(21, "Настя"));
        
        System.out.println(treeSet);
        var ages = treeSet.stream()
            .map(Human::age)
            .collect(Collectors.toList());
        
        assertThat(ages, contains(21, 22, 23));
        
        var treeSetByName = new TreeSet<Human>(Comparator.comparing(Human::name));
        treeSetByName.add(new Human(23, "Миша"));
        treeSetByName.add(new Human(22, "Вова"));
        treeSetByName.add(new Human(21, "Настя"));
        
        System.out.println(treeSetByName);
        var names = treeSetByName.stream()
            .map(Human::name)
            .collect(Collectors.toList());
        
        assertThat(names, contains("Вова", "Миша", "Настя"));
    }
    
    @Test
    void nullKeys_hashSet_test() {
        System.out.println(String.format("Object hashcode = %s", Objects.hashCode(new Object())));
        System.out.println(String.format("Null hashcode = %s", Objects.hashCode(null)));
        
        var hashSet = new HashSet<Integer>();
        hashSet.add(null);
        hashSet.add(1);
        System.out.println(hashSet);
        
        assertThat(hashSet, containsInAnyOrder(null, 1));
    }
    
    @Test
    void nullKeys_treeMap_test() {
        var treeSet = new TreeSet<Integer>();
        assertThrows(NullPointerException.class, () -> treeSet.add(null));
    }
    
    @Test
    void hashSet_Put_performance_test() {
        Runnable function = () -> {
            var set = new HashSet<Integer>();
            IntStream.range(0, 1_000_000).forEach(i ->
                set.add(i)
            );
        };
        logExecutionTime(function);
    }
    
    @Test
    void treeSet_Put_performance_test() {
        Runnable function = () -> {
            var set = new TreeSet<Integer>();
            IntStream.range(0, 1_000_000).forEach(i ->
                set.add(i)
            );
        };
        logExecutionTime(function);
    }
    
    @Test
    void treeSet_functionality_test() {
        var treeSet = new TreeSet<Human>(Comparator.comparing(Human::age));
        
        var вова = new Human(22, "Вова");
        var миша = new Human(23, "Миша");
        var настя = new Human(21, "Настя");
        
        treeSet.add(вова);
        treeSet.add(миша);
        treeSet.add(настя);
        
        System.out.println(treeSet);
        
        System.out.println(String.format("Получаем первый элемент %s", treeSet.first()));
        System.out.println(String.format("Получаем последний элемент %s", treeSet.last()));
        System.out.println(String.format("Получаем Настю через Вову %s", treeSet.lower(вова)));
        System.out.println(String.format("Получаем Мишу через Вову %s", treeSet.ceiling(вова)));
    }
    
    void logMemoryStat(Runnable r) {
        Runtime rt = Runtime.getRuntime();
        System.out.format("total mem: %d, freeMemory: %d, maxMemory: %d | start %n",
            rt.totalMemory(), rt.freeMemory(), rt.maxMemory());
        r.run();
        System.out.format("total mem: %d, freeMemory: %d, maxMemory: %d | end %n",
            rt.totalMemory(), rt.freeMemory(), rt.maxMemory());
    }
    
    void logExecutionTime(Runnable r) {
        var start = System.currentTimeMillis();
        r.run();
        var end = System.currentTimeMillis();
        System.out.println(String.format("выполнение заняло %s мс", end - start));
    }
    
    private record Human(int age, String name) {
    }
}
