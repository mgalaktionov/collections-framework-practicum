package com.example.ref;


import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;


import static org.awaitility.Awaitility.await;

class WeakHashMapTest {
    
    @Test
    void test_1() {
        // удаляется при следующем проходе GC
        var o1 = new Object(); // Strong
        var weakRef = new WeakReference<>(o1); // weak
        o1 = null;
    
        // удаляется при следующем проходе GC, но только если сильно не хватает памяти
        var o2 = new Object(); // Strong
        var softRef = new SoftReference<>(o2); // soft
        o2 = null;
    
        // Может быть удалена в любой момент
        var o3 = new Object(); // Strong
        var phantomRef = new PhantomReference<>(o3, new ReferenceQueue<>()); // phantom
        o3 = null;
    }
    
    @Test
    void test_2() {
        
        WeakHashMap<UUID, HeavyObject> map = new WeakHashMap<>();
        var obj1 = new HeavyObject();
        var obj1Id = UUID.randomUUID();
    
        var obj2 = new HeavyObject();
        var obj2Id = UUID.randomUUID();
    
        map.put(obj1Id, obj1);
        map.put(obj2Id, obj2);
    
        obj1Id = null;
        
        System.gc();
    
        await().atMost(10, TimeUnit.SECONDS)
            .until(() -> map.size() == 1);
        
        await().atMost(10, TimeUnit.SECONDS)
            .until(() -> map.containsKey(obj2Id));
        
        System.out.println(map);
        
    }
    
    private class HeavyObject {
        private List<Object> content;
        HeavyObject(){
            content = new ArrayList();
            for (int i = 0; i < 1_000_000; i++) {
                content.add(new String());
            }
        }
    }
}
