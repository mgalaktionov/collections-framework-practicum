package com.example.map;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HashVSTreeMapTest {
    
    @Test
    void hashMap_order_test() {
        var hashMap = new HashMap<Integer, String>();
        hashMap.put(4, "Four");
        hashMap.put(3, "Three");
        hashMap.put(2, "Two");
        hashMap.put(1, "One");
        
        System.out.println(hashMap);
        assertThat(hashMap.keySet(), containsInAnyOrder(1, 2, 3, 4));
    }
    
    @Test
    void treeMap_order_test() {
        var treeMap = new TreeMap<Integer, String>();
        treeMap.put(4, "Four");
        treeMap.put(3, "Three");
        treeMap.put(2, "Two");
        treeMap.put(1, "One");
        
        System.out.println(treeMap);
        assertThat(treeMap.keySet(), contains(1, 2, 3, 4));
    }
    
    @Test
    void treeMap_comparator_order_test() {
        var treeMapByAge = new TreeMap<Human, String>(Comparator.comparing(Human::age));
        treeMapByAge.put(new Human(23, "Миша"), "разработчик");
        treeMapByAge.put(new Human(22, "Вова"), "тестировщик");
        treeMapByAge.put(new Human(21, "Настя"), "дизайнер");
        
        System.out.println(treeMapByAge);
        var ages = treeMapByAge.keySet().stream()
            .map(Human::age)
            .collect(Collectors.toList());
        
        assertThat(ages, contains(21, 22, 23));
        
        var treeMapByName = new TreeMap<Human, String>(Comparator.comparing(Human::name));
        treeMapByName.put(new Human(23, "Миша"), "разработчик");
        treeMapByName.put(new Human(22, "Вова"), "тестировщик");
        treeMapByName.put(new Human(21, "Настя"), "дизайнер");
        
        System.out.println(treeMapByName);
        var names = treeMapByName.keySet().stream()
            .map(Human::name)
            .collect(Collectors.toList());
        
        assertThat(names, contains("Вова", "Миша", "Настя"));
    }
    
    @Test
    void nullKeys_hashMap_test() {
        System.out.println(String.format("Object hashcode = %s", Objects.hashCode(new Object())));
        System.out.println(String.format("Null hashcode = %s", Objects.hashCode(null)));
        
        var hashMap = new HashMap<Integer, String>();
        hashMap.put(null, null);
        hashMap.put(1, "one");
        System.out.println(hashMap);
        
        assertThat(hashMap.keySet(), containsInAnyOrder(null, 1));
        
        var treeMap = new TreeMap<Integer, String>();
        try {
            treeMap.put(null, null);
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }
    }
    
    @Test
    void nullKeys_treeMap_test() {
        var treeMap = new TreeMap<Integer, String>();
        assertThrows(NullPointerException.class, () -> treeMap.put(null, null));
    }
    
    @Test
    void hashMap_Put_performance_test() {
        Runnable function = () -> {
            var map = new HashMap<Integer, String>();
            IntStream.range(0, 1_000_000).forEach(i ->
                map.put(i, "")
            );
        };
        logExecutionTime(function);
    }
    
    @Test
    void treeMap_Put_performance_test() {
        Runnable function = () -> {
            var map = new TreeMap<Integer, String>();
            IntStream.range(0, 1_000_000).forEach(i ->
                map.put(i, "")
            );
        };
        logExecutionTime(function);
    }
    
    @Test
    void hashMap_Put_memory_test() {
        Runnable function = () -> {
            var map = new HashMap<Integer, String>();
            IntStream.range(0, 1_000_000).forEach(i ->
                map.put(i, "")
            );
        };
        logMemoryStat(function);
    }
    
    @Test
    void treeMap_Put_memory_test() {
        Runnable function = () -> {
            var map = new TreeMap<Integer, String>();
            IntStream.range(0, 1_000_000).forEach(i ->
                map.put(i, "")
            );
        };
        logMemoryStat(function);
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
