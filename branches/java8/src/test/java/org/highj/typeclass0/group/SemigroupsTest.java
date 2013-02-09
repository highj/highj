package org.highj.typeclass0.group;

import org.highj.data.collection.List;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SemigroupsTest {
    @Test
    public void testFirst() {
        Integer result = Semigroups.<Integer>first().fold(1, List.of(2,3,4,5));
        assertEquals(Integer.valueOf(1), result);
    }

    @Test
    public void testLast() {
        Integer result = Semigroups.<Integer>last().fold(1, List.of(2,3,4,5));
        assertEquals(Integer.valueOf(5), result);
    }

    @Test
    public void testDual() {
        Integer result = Semigroups.dual(Semigroups.<Integer>first()).fold(1, List.of(2,3,4,5));
        assertEquals(Integer.valueOf(5), result);
    }

    @Test
    public void testMin() {
        Integer result = Semigroups.<Integer>min().fold(27, List.of(25,11,64,57));
        assertEquals(Integer.valueOf(11), result);
    }

    @Test
    public void testMax() {
        Integer result = Semigroups.<Integer>max().fold(27, List.of(25,11,64,57));
        assertEquals(Integer.valueOf(64), result);
    }
}
