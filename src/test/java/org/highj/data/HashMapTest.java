package org.highj.data;

import org.highj.data.tuple.T2;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class HashMapTest {
    @Test
    public void testEmpty() {
        HashMap<String, Integer> emptyMap = HashMap.empty();
        assertThat(emptyMap).isEmpty();
        assertThat(emptyMap.isEmpty()).isTrue();
    }

    @Test
    public void isEmpty() {
        assertThat(HashMap.empty().isEmpty()).isTrue();
        assertThat(HashMap.empty().insert("one", 1).isEmpty()).isFalse();
        assertThat(HashMap.empty().insert("one", 1).delete("one").isEmpty()).isTrue();
    }

    @Test
    public void testIterator() {
        HashMap<String, Integer> m = HashMap.empty();
        java.util.List<Integer> ints = List.range(0, 1, 999).toJList();
        Collections.shuffle(ints, new Random(123));
        for (int i : ints) {
            m = m.insert(Integer.toHexString(i), i);
        }
        Iterator<T2<String, Integer>> iterator = m.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            T2<String, Integer> x = iterator.next();
            assertThat(Integer.toHexString(x._2())).isEqualTo(x._1());
        }
        assertThat(count).isEqualTo(1000);
    }

    @Test
    public void testSize() {
        assertThat(HashMap.empty().size()).isEqualTo(0);
        HashMap<String, Integer> map = HashMap.<String, Integer>empty().insert("one", 1).insert("two", 2).insert("three", 3);
        assertThat(map.size()).isEqualTo(3);

        HashMap<String, Integer> m = HashMap.empty();
        java.util.List<Integer> ints = List.range(0, 1, 999).toJList();
        Collections.shuffle(ints, new Random(123));
        for (int i : ints) {
            m = m.insert(Integer.toHexString(i), i);
        }
        assertThat(m.size()).isEqualTo(1000);
    }

    @Test
    public void testInsert() {
        HashMap<String, Integer> map = HashMap.<String, Integer>empty()
                                           .insert("one", 1).insert("two", 2).insert("three", 3).insert("one", 100).insert("four", 4);
        assertThat(map.lookup("one").get()).isEqualTo(100);
    }

    @Test
    public void testLookup() {
        HashMap<String, Integer> map = HashMap.<String, Integer>empty().insert("one", 1).insert("two", 2).insert("three", 3);
        assertThat(map.lookup("two").get()).isEqualTo(2);
        assertThat(map.lookup("four").isNothing());
    }

    @Test
    public void testDelete() {
        HashMap<String, Integer> map = HashMap.<String, Integer>empty()
                                           .insert("one", 1).insert("two", 2).insert("three", 3)
                                           .delete("four").delete("two");
        assertThat(map.lookup("one").isJust()).isTrue();
        assertThat(map.lookup("two").isNothing()).isTrue();
        assertThat(map.lookup("three").isJust()).isTrue();
    }

    @Test
    public void testToString() {
        HashMap<Object, Object> map = HashMap.empty().insert("one", 1).insert("two", 2).insert("three", 3);
        assertThat(map.toString()).contains("Leaf (one,1)");
        assertThat(map.toString()).contains("Leaf (two,2)");
        assertThat(map.toString()).contains("Leaf (three,3)");
    }

    @Test
    public void testCollisions() {
        HashMap<HashCollider, Integer> map = HashMap.<HashCollider, Integer>empty()
                                                 .insert(new HashCollider("one"), 1)
                                                 .insert(new HashCollider("two"), 2)
                                                 .insert(new HashCollider("three"), 3);
        assertThat(map.size()).isEqualTo(3);
        assertThat(map.lookup(new HashCollider("one")).get()).isEqualTo(1);
        assertThat(map.lookup(new HashCollider("two")).get()).isEqualTo(2);
        assertThat(map.lookup(new HashCollider("three")).get()).isEqualTo(3);
    }

    private static class HashCollider {
        public final String key;

        private HashCollider(String key) {
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            return key.equals(((HashCollider) o).key);
        }

        @Override
        public int hashCode() {
            return 11;
        }

        @Override
        public String toString() {
            return "#" + key;
        }
    }

}