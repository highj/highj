package org.highj.data;

import org.highj.data.num.Integers;
import org.highj.data.tuple.T2;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MapTest {

    private final static Map<String, Integer> aMap = Map.<String, Integer>empty().plus("x", 3).plus("y", 5).plus("z", 10);

    @Test
    public void test$() {
        assertThat(aMap.apply("y").toString()).isEqualTo("Just(5)");
        assertThat(aMap.apply("a").toString()).isEqualTo("Nothing");
    }

    @Test
    public void testGetOrElse() {
        assertThat(aMap.getOrElse("y", 42).toString()).isEqualTo("5");
        assertThat(aMap.getOrElse("a", 42).toString()).isEqualTo("42");
    }

    @Test
    public void testGet() {
        assertThat(aMap.get("y").toString()).isEqualTo("5");
        assertThatThrownBy(() -> aMap.get("a"))
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testPlusAB() {
        assertThat(Map.empty().plus("a", 20).toString()).isEqualTo("Map(a->20)");
        assertThat(aMap.plus("x", 13).toString()).isEqualTo("Map(x->13,y->5,z->10)");
        assertThat(aMap.plus("a", 20).toString()).isEqualTo("Map(a->20,x->3,y->5,z->10)");
    }

    @Test
    public void testPlusT2() {
        T2<String, Integer> t2 = T2.of("a", 20);
        assertThat(Map.<String, Integer>empty().plus(t2).toString()).isEqualTo("Map(a->20)");
        assertThat(aMap.plus(t2).toString()).isEqualTo("Map(a->20,x->3,y->5,z->10)");
    }

    @Test
    public void testMinus1() {
        assertThat(Map.empty().minus("a").toString()).isEqualTo("Map()");
        assertThat(aMap.minus("a").toString()).isEqualTo("Map(x->3,y->5,z->10)");
        assertThat(aMap.minus("y").toString()).isEqualTo("Map(x->3,z->10)");
    }

    @Test
    public void testIsEmpty() {
        assertThat(Map.empty().isEmpty()).isTrue();
        assertThat(aMap.isEmpty()).isFalse();
        assertThat(aMap.minus("y", "x", "z").isEmpty()).isTrue();
    }


    @Test
    public void testEmpty() {
        assertThat(Map.empty().toString()).isEqualTo("Map()");
    }

    @Test
    public void testOf0() {
        assertThat(Map.of().toString()).isEqualTo("Map()");
    }

    @Test
    public void testOfT2() {
        assertThat(Map.of(T2.of("y", 5), T2.of("z", 10), T2.of("x", 3)).toString())
            .isEqualTo("Map(x->3,y->5,z->10)");
    }

    @Test
    public void testOfIterable() {
        List<T2<String, Integer>> list = List.of(T2.of("y", 5), T2.of("z", 10), T2.of("x", 3));
        assertThat(Map.of(list).toString()).isEqualTo("Map(x->3,y->5,z->10)");
    }

    @Test
    public void testPlusT2s() {
        assertThat(aMap.plus(T2.of("a", 1), T2.of("x", 13)).toString())
            .isEqualTo("Map(a->1,x->13,y->5,z->10)");
    }

    @Test
    public void testMinusN() {
        assertThat(aMap.minus("a", "y", "b").toString()).isEqualTo("Map(x->3,z->10)");
    }

    @Test
    public void testPlusIterable() {
        List<T2<String, Integer>> list = List.of(T2.of("a", 1), T2.of("x", 13));
        assertThat(aMap.plus(list).toString()).isEqualTo("Map(a->1,x->13,y->5,z->10)");
    }

    @Test
    public void testMinusIterable() {
        List<String> list = List.of("a", "y");
        assertThat(aMap.minus(list).toString()).isEqualTo("Map(x->3,z->10)");
    }

    @Test
    public void testSize() {
        assertThat(Map.empty().size()).isEqualTo(0);
        assertThat(aMap.size()).isEqualTo(3);
    }

    @Test
    public void testF1() {
        Function<String, Maybe<Integer>> f1 = aMap.apply();
        assertThat(f1.apply("y").toString()).isEqualTo("Just(5)");
        assertThat(f1.apply("a").toString()).isEqualTo("Nothing");
    }

    @Test
    public void testIterator() {
        assertThat(Map.empty().iterator().hasNext()).isFalse();

        Iterator<T2<String, Integer>> it = aMap.iterator();
        assertThat(it.next().toString()).isEqualTo("(x,3)");
        assertThat(it.next().toString()).isEqualTo("(y,5)");
        assertThat(it.next().toString()).isEqualTo("(z,10)");
        assertThat(it.hasNext()).isFalse();
    }

    @Test
    public void testToString() {
        assertThat(Map.empty().toString()).isEqualTo("Map()");
        assertThat(aMap.toString()).isEqualTo("Map(x->3,y->5,z->10)");
    }

    @Test
    public void testKeys() {
        assertThat(Map.empty().keys().toString()).isEqualTo("Set()");
        assertThat(aMap.keys().toString()).isEqualTo("Set(x,y,z)");
    }

    @Test
    public void testValues() {
        assertThat(Map.empty().values().toString()).isEqualTo("Set()");
        assertThat(aMap.values().toString()).isEqualTo("Set(3,5,10)");
    }

    @Test
    public void testMap() {
        assertThat(aMap.map(Integers.negate).toString()).isEqualTo("Map(x->-3,y->-5,z->-10)");
    }
}