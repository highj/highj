package org.highj.data.collection;

import org.derive4j.hkt.__;
import org.highj.data.collection.set.SetMonadPlus;
import org.highj.typeclass0.group.Monoid;
import org.junit.Test;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

public class SetTest {

    @Test
    public void testAp() {
        Set<Function<Integer, Integer>> fns = Set.of(i -> i, i -> i * i, i -> i * i * i);
        assertThat(Set.<Integer>empty().ap(fns)).isEmpty();
        assertThat(Set.of(1, 2, 3).ap(fns)).containsExactlyInAnyOrder(1, 2, 3, 4, 9, 8, 27);
    }

    @Test
    public void testCount() {
        Predicate<Integer> even = i -> i % 2 == 0;
        assertThat(Set.<Integer>empty().count(even)).isEqualTo(0);
        assertThat(Set.of(7, 9, 11).count(even)).isEqualTo(0);
        assertThat(Set.of(2, 5, 6, 8).count(even)).isEqualTo(3);
    }

    @Test
    public void testBind() {
        Function<Integer, Set<Integer>> fn = i -> Set.of(i + 10, i + 20, i + 30);
        assertThat(Set.<Integer>empty().bind(fn)).isEmpty();
        assertThat(Set.of(1, 2).bind(fn)).containsExactlyInAnyOrder(11, 12, 21, 22, 31, 32);
    }

    @Test
    public void testEmpty() {
        assertThat(Set.empty()).isEmpty();
    }

    @Test
    public void testEquals() {
        Set s1 = Set.of(1, 2, 3, 4, 5, 6, 7);
        Set s2 = Set.of(6, 2, 1, 3, 4, 7, 5, 7, 7);
        Set s3 = Set.of(6, 2, 1, 3, 4, 7, 5, 8);
        assertThat(s1.equals(s1)).isTrue();
        assertThat(s1.equals(s2)).isTrue();
        assertThat(s2.equals(s1)).isTrue();
        assertThat(s1.equals(s3)).isFalse();
        assertThat(s2.equals(s3)).isFalse();
        assertThat(s3.equals(s1)).isFalse();
        assertThat(s3.equals(s1)).isFalse();
    }

    @Test
    public void testFilter() {
        Predicate<Integer> even = i -> i % 2 == 0;
        assertThat(Set.<Integer>empty().filter(even)).isEmpty();
        assertThat(Set.of(7, 9, 11).filter(even)).isEmpty();
        assertThat(Set.of(2, 5, 6, 8).filter(even)).containsExactlyInAnyOrder(2, 6, 8);
    }

    @Test
    public void testHashCode() {
        assertThat(Set.of().hashCode()).isEqualTo(Set.empty().hashCode());
        assertThat(Set.of("foo", "bar", "baz", "quux").hashCode()).isEqualTo(
                Set.of("bar", "foo", "quux", "baz").hashCode());
        assertThat(Set.of("foo", "bar", "baz", "quux").hashCode()).isNotEqualTo(
                Set.of("bra", "foo", "quux", "baz").hashCode());
    }

    @Test
    public void testIsEmpty() {
        assertThat(Set.empty().isEmpty()).isTrue();
        assertThat(Set.of(1).isEmpty()).isFalse();
        assertThat(Set.of(1, 2, 3).isEmpty()).isFalse();
    }

    @Test
    public void testIterator() {
        assertThat(Set.empty().iterator()).isEmpty();
        Set<Integer> set = Set.of(3, 5, 1, 2, 4, 6);
        assertThat(set.iterator()).containsExactlyInAnyOrder(3, 5, 1, 2, 4, 6);
    }

    @Test
    public void testJoin() {
        assertThat(Set.join(Set.empty())).isEmpty();
        Set<Set<Integer>> set = Set.of(Set.of(1, 3, 5), Set.<Integer>of(), Set.of(6, 4, 2), Set.of(1, 4, 5));
        assertThat(Set.join(set)).containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6);
    }

    @Test
    public void testMap() {
        Function<Integer, String> fn = i -> "" + i + i;
        assertThat(Set.<Integer>empty().map(fn)).isEmpty();
        assertThat(Set.of(3, 5, 1, 2, 4).map(fn)).containsExactlyInAnyOrder("11", "22", "33", "44", "55");
    }

    @Test
    public void testMinus() {
        Set<Integer> set = Set.of(1, 2, 3, 4, 5, 100);
        set = set.minus();
        assertThat(set).containsExactlyInAnyOrder(1, 2, 3, 4, 5, 100);
        set = set.minus(1);
        assertThat(set).containsExactlyInAnyOrder(2, 3, 4, 5, 100);
        set = set.minus(Set.empty());
        assertThat(set).containsExactlyInAnyOrder(2, 3, 4, 5, 100);
        set = set.minus(Set.of(5, 4, 3));
        assertThat(set).containsExactlyInAnyOrder(2, 100);
        set = set.minus(2, 2, 2);
        assertThat(set).containsExactlyInAnyOrder(100);
        set = set.minus(4711);
        assertThat(set).containsExactlyInAnyOrder(100);
        set = set.minus(100);
        assertThat(set).isEmpty();
        set = set.minus(100);
        assertThat(set).isEmpty();
    }

    @Test
    public void testMonadPlus() {
        SetMonadPlus monadPlus = Set.monadPlus;
        assertThat(monadPlus.pure(4)).containsExactlyInAnyOrder(4);
        assertThat(monadPlus.map(i -> i * i, Set.of(1, 2, 3))).containsExactlyInAnyOrder(1, 4, 9);
        assertThat(monadPlus.ap(Set.of(i -> i * i, i -> i * i * i), Set.of(1, 2, 3))).containsExactlyInAnyOrder(1, 4, 9, 8, 27);
        assertThat(monadPlus.bind(Set.of(1, 2, 3), i -> Set.of(i * i, i * i * i))).containsExactlyInAnyOrder(1, 4, 9, 8, 27);
        assertThat(monadPlus.join(Set.of(Set.of(1, 2, 3), Set.empty(), Set.of(3, 4, 5)))).containsExactlyInAnyOrder(1, 2, 3, 4, 5);
        assertThat(monadPlus.mzero()).isEmpty();
        assertThat(monadPlus.mplus(Set.of(1, 2, 3), Set.of(3, 4, 5))).containsExactlyInAnyOrder(1, 2, 3, 4, 5);

        Function<String, __<Set.µ, Either<String, String>>> substrings = s -> s.length() == 1
                ? Set.of(Either.newRight(s))
                : Set.of(Either.newRight(s), Either.newLeft(s.substring(1)), Either.newLeft(s.substring(0, s.length() - 1)));
        Set<String> set = monadPlus.tailRec(substrings, "abc");
        assertThat(set).containsExactlyInAnyOrder("a", "b", "c", "ab", "bc", "abc");
    }

    @Test
    public void testMonoid() {
        Monoid<Set<Integer>> monoid = Set.monoid();
        assertThat(monoid.identity()).isEmpty();
        assertThat(monoid.apply(Set.of(1, 2, 3), Set.empty())).containsExactlyInAnyOrder(1, 2, 3);
        assertThat(monoid.apply(Set.empty(), Set.of(1, 2, 3))).containsExactlyInAnyOrder(1, 2, 3);
        assertThat(monoid.apply(Set.of(1, 2, 3), Set.of(3, 4, 5))).containsExactlyInAnyOrder(1, 2, 3, 4, 5);
        assertThat(monoid.fold(List.nil())).isEmpty();
        assertThat(monoid.fold(List.of(Set.empty(), Set.of(1, 2), Set.empty(), Set.of(1, 3))))
                .containsExactlyInAnyOrder(1, 2, 3);
    }

    @Test
    public void testPlus() {
        assertThat(Set.<Integer>empty().plus(12)).containsExactlyInAnyOrder(12);
        assertThat(Set.of(12).plus(12)).containsExactlyInAnyOrder(12);
        assertThat(Set.of(0, 8, 15).plus()).containsExactlyInAnyOrder(0, 8, 15);
        assertThat(Set.of(0, 8, 15).plus(0, 8, 15)).containsExactlyInAnyOrder(0, 8, 15);
        assertThat(Set.of(0, 8, 15).plus(16)).containsExactlyInAnyOrder(0, 8, 15, 16);
        assertThat(Set.of(0, 8, 15).plus(0, 8, 15, 16)).containsExactlyInAnyOrder(0, 8, 15, 16);
        assertThat(Set.of(0, 8, 15).plus(Set.empty())).containsExactlyInAnyOrder(0, 8, 15);
        assertThat(Set.of(0, 8, 15).plus(Set.of(0, 8, 15))).containsExactlyInAnyOrder(0, 8, 15);
        assertThat(Set.of(0, 8, 15).plus(Set.of(16))).containsExactlyInAnyOrder(0, 8, 15, 16);
        assertThat(Set.of(0, 8, 15).plus(Set.of(0, 8, 15, 16))).containsExactlyInAnyOrder(0, 8, 15, 16);
    }

    @Test
    public void testSize() {
        assertThat(Set.empty().size()).isEqualTo(0);
        assertThat(Set.of(1, 1, 1).size()).isEqualTo(1);
        assertThat(Set.of(1, 2, 3).size()).isEqualTo(3);
        assertThat(Set.of(4, 5, 6, 1, 2, 3, 7, 8, 9).size()).isEqualTo(9);
    }

    @Test
    public void testTest() {
        Set<Integer> set = Set.of(23, 17, 12, 23, 55, 26, 73, 33, 12);
        assertThat(set.test(3)).isFalse();
        assertThat(set.test(23)).isTrue();
        assertThat(set.test(73)).isTrue();
    }

    @Test
    public void testToJSet() {
        assertThat(Set.empty().toJSet()).isEmpty();
        java.util.Set<Integer> set = Set.of(3, 5, 1, 2, 4, 6).toJSet();
        assertThat(set).containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6);
    }

    @Test
    public void testToString() {
        assertThat(Set.empty().toString()).isEqualTo("Set()");
        assertThat(Set.of(0, 8, 15).toString()).isEqualTo("Set(0,8,15)");
    }
}
