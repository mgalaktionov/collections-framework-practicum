package com.example.iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.example.iterator.ListIteratorDemo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class ListIteratorDemoTest {
    
    @Nested
    @DisplayName("При использовании метода removeElementFromList: ")
    class RemoveElementFromListTest {
        
        @Test
        @DisplayName("Количество элементов уменьшается, если условие выполнено хотя бы 1 раз")
        void test_1() {
            var list = new ArrayList<Integer>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
            
            ListIteratorDemo.removeElementFromList(
                list,
                (Predicate<Integer>) i -> i.equals(2)
            );
            
            assertEquals(9, list.size());
        }
        
        @Test
        @DisplayName("удаляемый элемент соответствует условию")
        void test_2() {
            var list = new ArrayList<Integer>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
            
            ListIteratorDemo.removeElementFromList(
                list,
                (Predicate<Integer>) i -> i.equals(2)
            );
            
            for (Integer i : list) {
                assertNotEquals(2, (int) i);
            }
        }
    
        @Test
        @DisplayName("если несколько элементов соответствуют условию, то они удаляются")
        void test_3() {
            var list = new ArrayList<Integer>(List.of(2, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        
            ListIteratorDemo.removeElementFromList(
                list,
                (Predicate<Integer>) i -> i.equals(2)
            );
        
            assertEquals(8, list.size());
        }
    }
}
