package org.highj.util;

import org.highj.data.List;
import org.highj.data.tuple.T2;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class IteratorsTest {

    @Test
    public void testEmptyIterator() {
        assertThat(Iterators.emptyIterator().hasNext()).isFalse();
        assertThatThrownBy(() -> Iterators.emptyIterator().next())
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testValueIterator() {
        assertThat(toIterable(Iterators.valueIterator(0, 8, 15))).containsExactly(0, 8, 15);
    }

    @Test
    public void testCyclicIterator() {
        assertThat(toIterable(Iterators.cyclicIterator())).isEmpty();

        Iterator<Integer> single = Iterators.cyclicIterator(42);
        assertThat(single.next()).isEqualTo(42);
        assertThat(single.next()).isEqualTo(42);
        assertThat(single.next()).isEqualTo(42);

        Iterator<Integer> multi = Iterators.cyclicIterator(0, 8, 15);
        assertThat(multi.next()).isEqualTo(0);
        assertThat(multi.next()).isEqualTo(8);
        assertThat(multi.next()).isEqualTo(15);
        assertThat(multi.next()).isEqualTo(0);
        assertThat(multi.next()).isEqualTo(8);
        assertThat(multi.next()).isEqualTo(15);
        assertThat(multi.next()).isEqualTo(0);
    }

    @Test
    public void testConcat() {
        assertThat(toIterable(Iterators.concat())).isEmpty();
        assertThat(toIterable(Iterators.concat(Iterators.emptyIterator()))).isEmpty();
        assertThat(toIterable(Iterators.concat(List.of(0, 8, 15).iterator()))).containsExactly(0, 8, 15);
        assertThat(toIterable(Iterators.concat(
            List.of(0, 8, 15).iterator(),
            List.of(47, 11).iterator())))
            .containsExactly(0, 8, 15, 47, 11);
        assertThat(toIterable(Iterators.concat(
            Iterators.emptyIterator(),
            List.of(0, 8, 15).iterator(),
            Iterators.emptyIterator(),
            Iterators.emptyIterator(),
            List.of(47, 11).iterator(),
            Iterators.emptyIterator())))
            .containsExactly(0, 8, 15, 47, 11);
    }

    @Test
    public void testDrop() {
        assertThat(toIterable(Iterators.drop(-10, List.of().iterator()))).isEmpty();
        assertThat(toIterable(Iterators.drop(0, List.of().iterator()))).isEmpty();
        assertThat(toIterable(Iterators.drop(100, List.of().iterator()))).isEmpty();
        assertThat(toIterable(Iterators.drop(-10, List.of(0, 8, 15).iterator()))).containsExactly(0, 8, 15);
        assertThat(toIterable(Iterators.drop(0, List.of(0, 8, 15).iterator()))).containsExactly(0, 8, 15);
        assertThat(toIterable(Iterators.drop(1, List.of(0, 8, 15).iterator()))).containsExactly(8, 15);
        assertThat(toIterable(Iterators.drop(2, List.of(0, 8, 15).iterator()))).containsExactly(15);
        assertThat(toIterable(Iterators.drop(2, List.of(0, 8, 15).iterator()))).containsExactly(15);
        assertThat(toIterable(Iterators.drop(3, List.of(0, 8, 15).iterator()))).isEmpty();
        assertThat(toIterable(Iterators.drop(100, List.of(0, 8, 15).iterator()))).isEmpty();
    }

    @Test
    public void zipWith() {
        assertThat(toIterable(Iterators.zipWith(List.of().iterator(), List.of().iterator(), (x, y) -> 3))).isEmpty();
        assertThat(toIterable(Iterators.zipWith(List.of(12, 14).iterator(), List.of().iterator(), (x, y) -> 3))).isEmpty();
        assertThat(toIterable(Iterators.zipWith(List.of().iterator(), List.of(12, 14).iterator(), (x, y) -> 3))).isEmpty();
        assertThat(toIterable(Iterators.zipWith(List.of(2, 4).iterator(), List.of(10, 20).iterator(), Integer::sum))).containsExactly(12, 24);
        assertThat(toIterable(Iterators.zipWith(List.of(2, 4, 6).iterator(), List.of(10, 20).iterator(), Integer::sum))).containsExactly(12, 24);
        assertThat(toIterable(Iterators.zipWith(List.of(2, 4).iterator(), List.of(10, 20, 30).iterator(), Integer::sum))).containsExactly(12, 24);
    }

    @Test
    public void zip() {
        assertThat(toIterable(Iterators.zip(List.of().iterator(), List.of().iterator()))).isEmpty();
        assertThat(toIterable(Iterators.zip(List.of(12, 14).iterator(), List.of().iterator()))).isEmpty();
        assertThat(toIterable(Iterators.zip(List.of().iterator(), List.of(12, 14).iterator()))).isEmpty();
        assertThat(toIterable(Iterators.zip(List.of(2, 4).iterator(), List.of(10, 20).iterator()))).containsExactly(T2.of(2, 10), T2.of(4, 20));
        assertThat(toIterable(Iterators.zip(List.of(2, 4, 6).iterator(), List.of(10, 20).iterator()))).containsExactly(T2.of(2, 10), T2.of(4, 20));
        assertThat(toIterable(Iterators.zip(List.of(2, 4).iterator(), List.of(10, 20, 30).iterator()))).containsExactly(T2.of(2, 10), T2.of(4, 20));
    }

    private static <T> Iterable<T> toIterable(Iterator<T> it) {
        return () -> it;
    }

}