package org.example.iterator;

import java.util.ArrayList;
import java.util.function.Predicate;

public class ListIteratorDemo {
    
    public static void removeElementFromList(ArrayList<Integer> list, Predicate condition) {
        
        // Bad
        for(Integer current : list) {
            if (condition.test(current)) {
                list.remove(current);
            }
        }
        
        // Good
//        for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext(); ) {
//            Integer current = iterator.next();
//            if (condition.test(current)) {
//                iterator.remove();
//            }
//        }
        
        // Oneliner
//        list.removeIf(condition::test);
    }
}
