package org.highj.util;

import org.highj.data.collection.List;
import org.highj.data.collection.Set;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IterablesTest {
    @Test
    public void testEmptyIterable() throws Exception {
         assertThat(Iterables.emptyIterable()).isEmpty();
    }

    @Test
    public void testValueIterable() throws Exception {
         assertThat(Iterables.valueIterable()).isEmpty();
         assertThat(Iterables.valueIterable(42)).containsExactly(42);
         assertThat(Iterables.valueIterable(0,8,15)).containsExactly(0,8,15);
    }

    @Test
    public void testCyclicIterable() throws Exception {
          assertThat(Iterables.cyclicIterable()).isEmpty();
          assertThat(Iterables.cyclicIterable(42)).startsWith(42,42,42);
          assertThat(Iterables.cyclicIterable(0,8,15)).startsWith(0,8,15,0,8,15,0,8,15);
    }

    @Test
    public void testConcat() throws Exception {
        assertThat(Iterables.concat()).isEmpty();
        assertThat(Iterables.concat(List.nil())).isEmpty();
        assertThat(Iterables.concat(List.of(0,8,15))).containsExactly(0,8,15);
        assertThat(Iterables.concat(List.of(0,8,15), List.nil(), Set.of(4), List.nil())).containsExactly(0,8,15,4);
    }

    @Test
    public void testReverseIterable() throws Exception {
        assertThat(Iterables.reverseIterable()).isEmpty();
        assertThat(Iterables.reverseIterable(42)).containsExactly(42);
        assertThat(Iterables.reverseIterable(0,8,15)).containsExactly(15,8,0);
    }

    @Test
    public void testDrop() throws Exception {
        assertThat(Iterables.drop(-10, List.of())).isEmpty();
        assertThat(Iterables.drop(0, List.of())).isEmpty();
        assertThat(Iterables.drop(100, List.of())).isEmpty();
        assertThat(Iterables.drop(-10, List.of(0,8,15))).containsExactly(0,8,15);
        assertThat(Iterables.drop(0, List.of(0,8,15))).containsExactly(0,8,15);
        assertThat(Iterables.drop(1, List.of(0,8,15))).containsExactly(8,15);
        assertThat(Iterables.drop(2, List.of(0,8,15))).containsExactly(15);
        assertThat(Iterables.drop(2, List.of(0,8,15))).containsExactly(15);
        assertThat(Iterables.drop(3, List.of(0,8,15))).isEmpty();
        assertThat(Iterables.drop(100, List.of(0,8,15))).isEmpty();
    }
}