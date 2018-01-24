package org.highj.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.highj.data.List;
import org.highj.data.tuple.T2;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class IteratorsTest {

    @Test(expected = NoSuchElementException.class)
    public void testEmptyIterator() throws Exception {
        assertThat(Iterators.emptyIterator().hasNext()).isFalse();
        Iterators.emptyIterator().next();
    }

    @Test
    public void testValueIterator() throws Exception {
        assertThat(Iterators.valueIterator(0,8,15)).containsExactly(0,8,15);
    }

    @Test
    public void testCyclicIterator() throws Exception {
        assertThat(Iterators.cyclicIterator()).isEmpty();

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
    public void testConcat() throws Exception {
        assertThat(Iterators.concat()).isEmpty();
        assertThat(Iterators.concat(Iterators.emptyIterator())).isEmpty();
        assertThat(Iterators.concat(List.of(0,8,15).iterator())).containsExactly(0,8,15);
        assertThat(Iterators.concat(
                List.of(0,8,15).iterator(),
                List.of(47,11).iterator()))
                .containsExactly(0,8,15,47,11);
        assertThat(Iterators.concat(
                Iterators.emptyIterator(),
                List.of(0,8,15).iterator(),
                Iterators.emptyIterator(),
                Iterators.emptyIterator(),
                List.of(47,11).iterator(),
                Iterators.emptyIterator()))
                .containsExactly(0,8,15,47,11);
    }

    @Test
    public void testDrop() throws Exception {
        assertThat(Iterators.drop(-10, List.of().iterator())).isEmpty();
        assertThat(Iterators.drop(0, List.of().iterator())).isEmpty();
        assertThat(Iterators.drop(100, List.of().iterator())).isEmpty();
        assertThat(Iterators.drop(-10, List.of(0,8,15).iterator())).containsExactly(0,8,15);
        assertThat(Iterators.drop(0, List.of(0,8,15).iterator())).containsExactly(0,8,15);
        assertThat(Iterators.drop(1, List.of(0,8,15).iterator())).containsExactly(8,15);
        assertThat(Iterators.drop(2, List.of(0,8,15).iterator())).containsExactly(15);
        assertThat(Iterators.drop(2, List.of(0,8,15).iterator())).containsExactly(15);
        assertThat(Iterators.drop(3, List.of(0,8,15).iterator())).isEmpty();
        assertThat(Iterators.drop(100, List.of(0,8,15).iterator())).isEmpty();
    }

    @Test
    public void zipWith() {
        assertThat(Iterators.zipWith(List.of().iterator(), List.of().iterator(), (x,y) -> 3)).isEmpty();
        assertThat(Iterators.zipWith(List.of(12, 14).iterator(), List.of().iterator(), (x,y) -> 3)).isEmpty();
        assertThat(Iterators.zipWith(List.of().iterator(), List.of(12, 14).iterator(), (x,y) -> 3)).isEmpty();
        assertThat(Iterators.zipWith(List.of(2,4).iterator(), List.of(10, 20).iterator(), (x,y) -> x + y)).containsExactly(12,24);
        assertThat(Iterators.zipWith(List.of(2,4,6).iterator(), List.of(10, 20).iterator(), (x,y) -> x + y)).containsExactly(12,24);
        assertThat(Iterators.zipWith(List.of(2,4).iterator(), List.of(10, 20, 30).iterator(), (x,y) -> x + y)).containsExactly(12,24);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void zip() {
        assertThat(Iterators.zip(List.of().iterator(), List.of().iterator())).isEmpty();
        assertThat(Iterators.zip(List.of(12, 14).iterator(), List.of().iterator())).isEmpty();
        assertThat(Iterators.zip(List.of().iterator(), List.of(12, 14).iterator())).isEmpty();
        assertThat(Iterators.zip(List.of(2,4).iterator(), List.of(10, 20).iterator())).containsExactly(T2.of(2,10), T2.of(4, 20));
        assertThat(Iterators.zip(List.of(2,4,6).iterator(), List.of(10, 20).iterator())).containsExactly(T2.of(2,10), T2.of(4, 20));
        assertThat(Iterators.zip(List.of(2,4).iterator(), List.of(10, 20, 30).iterator())).containsExactly(T2.of(2,10), T2.of(4, 20));
    }


}