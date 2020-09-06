package org.highj.typeclass2.bifoldable;

import org.highj.data.Maybe;
import org.highj.data.num.Integers;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.t2.T2Bifoldable;
import org.highj.function.Strings;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class BifoldableTest {

    @Test
    public void bifold() {
        T2Bifoldable bifoldable = T2.bifoldable;
        String result = bifoldable.bifold(Strings.monoid, T2.of("foo", "bar"));
        assertThat(result).isEqualTo("foobar");
    }

    @Test
    public void bifold1() {
        T2Bifoldable bifoldable = T2.bifoldable;
        Maybe<String> result = bifoldable.bifold1(Strings.monoid, T2.of("foo", "bar"));
        assertThat(result).containsExactly("foobar");
    }

    @Test
    public void bifoldMap() {
        T2Bifoldable bifoldable = T2.bifoldable;
        Integer result = bifoldable.bifoldMap(Integers.additiveGroup, String::length, String::length, T2.of("three", "four"));
        assertThat(result).isEqualTo(9);
    }

    @Test
    public void bifoldMap1() {
        T2Bifoldable bifoldable = T2.bifoldable;
        Maybe<Integer> result = bifoldable.bifoldMap1(Integers.additiveGroup, String::length, String::length, T2.of("three", "four"));
        assertThat(result).containsExactly(9);
    }

    @Test
    public void bifoldr() {
        T2Bifoldable bifoldable = T2.bifoldable;
        Integer result = bifoldable.bifoldr((s, i) -> i * s.length(), (a, b) -> b - a, 10, T2.of("foo", 4));
        assertThat(result).isEqualTo(26);  //(10 * 3) - 4
    }

    @Test
    public void bifoldl() {
        T2Bifoldable bifoldable = T2.bifoldable;
        Integer result = bifoldable.bifoldl((i, s) -> i * s.length(), (a, b) -> a - b, 10, T2.of("foo", 4));
        assertThat(result).isEqualTo(18);  //(10 - 4) * 3
    }
}