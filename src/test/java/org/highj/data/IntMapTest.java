/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data;

import org.highj.data.tuple.T2;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author clintonselke
 */
public class IntMapTest {
    @Test
    public void testLookupRange() {
        IntMap<String> m = IntMap.empty();
        java.util.List<Integer> ints = List.range(0, 1, 999).toJList();
        Collections.shuffle(ints, new Random(123));
        for (int i : ints) {
            m = m.insert(i, Integer.toHexString(i));
        }
        for (int i = 0; i < 1000; ++i) {
            assertThat(m.lookup(i).toString()).isEqualTo("Just(" + Integer.toHexString(i) + ")");
        }
    }

    @Test
    public void testIterator() {
        IntMap<String> m = IntMap.empty();
        java.util.List<Integer> ints = List.range(0, 1, 999).toJList();
        Collections.shuffle(ints, new Random(123));
        for (int i : ints) {
            m = m.insert(i, Integer.toHexString(i));
        }
        Iterator<T2<Integer, String>> iterator = m.iterator();
        while (iterator.hasNext()) {
            T2<Integer, String> x = iterator.next();
            assertThat(Integer.toHexString(x._1())).isEqualTo(x._2());
        }
    }

    @Test
    public void testLookup() {
        IntMap<String> m = IntMap.empty();
        List<Integer> ints = List.of(1000, 5, 3, 5, 6, 8, 1, 99, -150, -1000, 8);
        for (int i : ints) {
            m = m.insert(i, Integer.toHexString(i));
        }
        for (int i : ints) {
            assertThat(m.lookup(i))
                .isEqualTo(Maybe.Just(Integer.toHexString(i)))
                .withFailMessage(i + " not found");
        }
        for (int i : List.of(-1, 2, 4, 7, 9, 98, -99, 100, 150, 1001, Integer.MAX_VALUE, Integer.MIN_VALUE)) {
            assertThat(m.lookup(i).isNothing()).isTrue().withFailMessage(i + " found");
        }
    }

    @Test
    public void testSize() {
        IntMap<String> m = IntMap.empty();
        assertThat(m.size()).isEqualTo(0);

        List<Integer> ints = List.of(1000, 5, 3, 5, 6, 8, 1, 99, -150, -1000, 8);
        for (int i : ints) {
            m = m.insert(i, Integer.toHexString(i));
        }
        assertThat(m.size()).isEqualTo(9);
    }

    @Test
    public void testIsEmpty() {
        IntMap<String> m = IntMap.empty();
        assertThat(m.isEmpty()).isTrue();
        List<Integer> ints = List.of(1000, 5, 3, 5, 6, 8, 1, 99, -150, -1000, 8);
        for (int i : ints) {
            m = m.insert(i, Integer.toHexString(i));
            assertThat(m.isEmpty()).isFalse();
        }
    }

    @Test
    public void testDelete() {
        IntMap<String> m = IntMap.empty();
        assertThat(m.isEmpty()).isTrue();
        List<Integer> ints = List.of(1000, 5, 3, 5, 6, 8, 1, 99, -150, -1000, 8);
        for (int i : ints) {
            m = m.insert(i, Integer.toHexString(i));
        }
        for (int i : ints) {
            assertThat(m.delete(i).lookup(i).isNothing()).isTrue();
        }
        for (int i : List.of(-1, 2, 4, 7, 9, 98, -99, 100, 150, 1001, Integer.MAX_VALUE, Integer.MIN_VALUE)) {
            assertThat(m.delete(i).lookup(i).isNothing()).isTrue();
        }
        for (int i : ints) {
            m = m.delete(i);
        }
        assertThat(m.isEmpty()).isTrue();
    }

}
