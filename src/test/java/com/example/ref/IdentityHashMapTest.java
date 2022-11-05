package com.example.ref;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class IdentityHashMapTest {
    
    @Test
    void test_1() {
        
        var id = UUID.randomUUID();
        var key1 = new Key(id, 1);
        var key2 = new Key(id, 1);
    
        assertNotSame(key1, key2);
        assertEquals(key1, key2);
        
        var simpleHashMap = new HashMap<Key, String>();
        simpleHashMap.put(key1, "First value");
        simpleHashMap.put(key2, "Second value");
        
        // Тут спросить что будет
        
        assertEquals(1, simpleHashMap.size());
        assertEquals("Second value", simpleHashMap.get(key1));
    }
    
    @Test
    void test_2() {
        
        var id = UUID.randomUUID();
        var key1 = new Key(id, 1);
        var key2 = new Key(id, 1);
        
        assertNotSame(key1, key2);
        assertEquals(key1, key2);
        
        var identityHashMap = new IdentityHashMap<Key, String>();
        identityHashMap.put(key1, "First value");
        identityHashMap.put(key2, "Second value");
        
        // Тут спросить что будет
        
        assertEquals(2, identityHashMap.size());
        assertEquals("First value", identityHashMap.get(key1));
        assertEquals("Second value", identityHashMap.get(key2));
    }
    
    @AllArgsConstructor
    @EqualsAndHashCode(of = {"id", "version"})
    @ToString
    private class Key {
        private final UUID id;
        private final int version;
    }
}
