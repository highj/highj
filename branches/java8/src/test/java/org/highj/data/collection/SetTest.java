package org.highj.data.collection;

import org.highj._;
import org.highj.function.F1;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

public class SetTest {
    @Test
    public void test$()  {
        Set<Integer> set = Set.of(23, 17, 12, 23, 55, 26, 73, 33, 12);
        assertFalse(set.$(3));
        set = set.plus(3);
        assertTrue(set.$(3));
        assertFalse(set.$(5));
        set = set.minus(4);
        assertTrue(set.$(3));
        set = set.minus(3);
        assertFalse(set.$(3));
    }

    @Test
    public void testPlus() {
        java.util.List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Set<Integer> set = Set.of(list);
        for(int i = 0; i < 20; i++) {
           Collections.shuffle(list);
           Set<Integer> set1 = Set.of(list);
           assertEquals(set.toString(), set1.toString());
        }    
    }

    @Test
    public void testMinus()  {
        java.util.List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        for(int i = 0; i < 20; i++) {
            Collections.shuffle(list);
            Set<Integer> set = Set.of(list);
            set = set.plus(100);
            Collections.shuffle(list);
            set = set.minus(list);
            assertEquals("Set(100)", set.toString());
        }
    }

    @Test
    public void testIsEmpty()  {
        Set<Integer> set = Set.of(3, 5);
        assertFalse(set.isEmpty());
        set = set.minus(3);
        assertFalse(set.isEmpty());
        set = set.minus(3);
        assertFalse(set.isEmpty());
        set = set.minus(4);
        assertFalse(set.isEmpty());
        set = set.minus(5);
        assertTrue(set.isEmpty());
    }

    @Test
    public void testEmpty() {
        Set<Integer> set = Set.of();
        assertTrue(set.isEmpty());
        set = set.plus(3);
        assertFalse(set.isEmpty());
        set = set.minus(4);
        assertFalse(set.isEmpty());
        set = set.minus(3);
        assertTrue(set.isEmpty());
    }

    @Test
    public void testIterator()  {
        Set<Integer> set = Set.of(3, 5, 1, 2, 4, 6);
        java.util.Set<Integer> jSet = new HashSet<Integer>();
        jSet.addAll(Arrays.asList(3,5,1,2,4,6));
        for(Integer i : set) {
            assertTrue(jSet.remove(i));
        }
        assertTrue(jSet.isEmpty());
    }

    @Test
    public void testToString() {
        Set<Integer> set = Set.empty();
        assertEquals("Set()", set.toString());
        set = org.highj.data.collection.Set.of(3, 5, 1, 2, 4, 6);
        assertEquals("Set(1,2,3,4,5,6)", set.toString());
    }
    
    @Test
    public void testNoDuplicates() {
        Set<Integer> set = Set.of(3, 4, 6, 3, 5, 6, 1, 3, 2, 3, 4, 5, 5, 6, 2, 7, 8, 9, 9);
        assertEquals("Set(1,2,3,4,5,6,7,8,9)", set.toString());
    }

    @Test
    public void testMap() {
        F1<Integer, String> fn = new F1<Integer, String>() {
            @Override
            public String $(Integer integer) {
                return "" + integer + integer;
            }
        };

        Set<Integer> set = Set.empty();
        assertEquals("Set()", set.map(fn).toString());
        set = Set.of(3, 5, 1, 2, 4, 6);
        assertEquals("Set(11,22,33,44,55,66)", set.map(fn).toString());
    }

    @Test
    public void testJoin() {
        Set<Set<Integer>> set = Set.of(Set.of(1,3,5), Set.<Integer>of(), Set.of(6,4,2), Set.of(1,4,5));
        assertEquals("Set(1,2,3,4,5,6)", Set.join(set).toString());
        Set<_<Set.µ,Integer>> set_ = Set.<_<Set.µ,Integer>>of(Set.of(1,3,5), Set.<Integer>of(), Set.of(6,4,2), Set.of(1,4,5));
        assertEquals("Set(1,2,3,4,5,6)", Set.monadPlus.join(set_).toString());
    }

    @Test
    public void testToJSet() {
        java.util.Set set = Set.of(3, 5, 1, 2, 4, 6).toJSet();
        assertEquals("[1, 2, 3, 4, 5, 6]", set.toString());
    }

}
