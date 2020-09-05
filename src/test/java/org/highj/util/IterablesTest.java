package org.highj.util;

import org.highj.data.List;
import org.highj.data.Set;
import org.highj.data.tuple.T2;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IterablesTest {
    @Test
    public void emptyIterable() {
        assertThat(Iterables.emptyIterable()).isEmpty();
    }

    @Test
    public void valueIterable() {
        assertThat(Iterables.valueIterable()).isEmpty();
        assertThat(Iterables.valueIterable(42)).containsExactly(42);
        assertThat(Iterables.valueIterable(0, 8, 15)).containsExactly(0, 8, 15);
    }

    @Test
    public void cyclicIterable() {
        assertThat(Iterables.cyclicIterable()).isEmpty();
        assertThat(Iterables.cyclicIterable(42)).startsWith(42, 42, 42);
        assertThat(Iterables.cyclicIterable(0, 8, 15)).startsWith(0, 8, 15, 0, 8, 15, 0, 8, 15);
    }

    @Test
    public void concat() {
        assertThat(Iterables.concat()).isEmpty();
        assertThat(Iterables.concat(List.Nil())).isEmpty();
        assertThat(Iterables.concat(List.of(0, 8, 15))).containsExactly(0, 8, 15);
        assertThat(Iterables.concat(List.of(0, 8, 15), List.Nil(), Set.of(4), List.Nil())).containsExactly(0, 8, 15, 4);
    }

    @Test
    public void reverseIterable() {
        assertThat(Iterables.reverseIterable()).isEmpty();
        assertThat(Iterables.reverseIterable(42)).containsExactly(42);
        assertThat(Iterables.reverseIterable(0, 8, 15)).containsExactly(15, 8, 0);
    }

    @Test
    public void drop() {
        assertThat(Iterables.drop(-10, List.of())).isEmpty();
        assertThat(Iterables.drop(0, List.of())).isEmpty();
        assertThat(Iterables.drop(100, List.of())).isEmpty();
        assertThat(Iterables.drop(-10, List.of(0, 8, 15))).containsExactly(0, 8, 15);
        assertThat(Iterables.drop(0, List.of(0, 8, 15))).containsExactly(0, 8, 15);
        assertThat(Iterables.drop(1, List.of(0, 8, 15))).containsExactly(8, 15);
        assertThat(Iterables.drop(2, List.of(0, 8, 15))).containsExactly(15);
        assertThat(Iterables.drop(2, List.of(0, 8, 15))).containsExactly(15);
        assertThat(Iterables.drop(3, List.of(0, 8, 15))).isEmpty();
        assertThat(Iterables.drop(100, List.of(0, 8, 15))).isEmpty();
    }

    @Test
    public void zipWith() {
        assertThat(Iterables.zipWith(List.of(), List.of(), (x, y) -> 3)).isEmpty();
        assertThat(Iterables.zipWith(List.of(12, 14), List.of(), (x, y) -> 3)).isEmpty();
        assertThat(Iterables.zipWith(List.of(), List.of(12, 14), (x, y) -> 3)).isEmpty();
        assertThat(Iterables.zipWith(List.of(2, 4), List.of(10, 20), (x, y) -> x + y)).containsExactly(12, 24);
        assertThat(Iterables.zipWith(List.of(2, 4, 6), List.of(10, 20), (x, y) -> x + y)).containsExactly(12, 24);
        assertThat(Iterables.zipWith(List.of(2, 4), List.of(10, 20, 30), (x, y) -> x + y)).containsExactly(12, 24);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void zip() {
        assertThat(Iterables.zip(List.of(), List.of())).isEmpty();
        assertThat(Iterables.zip(List.of(12, 14), List.of())).isEmpty();
        assertThat(Iterables.zip(List.of(), List.of(12, 14))).isEmpty();
        assertThat(Iterables.zip(List.of(2, 4), List.of(10, 20))).containsExactly(T2.of(2, 10), T2.of(4, 20));
        assertThat(Iterables.zip(List.of(2, 4, 6), List.of(10, 20))).containsExactly(T2.of(2, 10), T2.of(4, 20));
        assertThat(Iterables.zip(List.of(2, 4), List.of(10, 20, 30))).containsExactly(T2.of(2, 10), T2.of(4, 20));
    }

}