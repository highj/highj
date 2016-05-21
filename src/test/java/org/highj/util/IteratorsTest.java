package org.highj.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.highj.data.List;
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

}