package org.highj.data.tuple;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.HList;
import org.highj.data.eq.Eq;
import org.highj.data.num.BigIntegers;
import org.highj.data.num.Integers;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.data.tuple.t2.T2Comonad;
import org.highj.data.tuple.t2.T2Group;
import org.highj.data.tuple.t2.T2Monoid;
import org.highj.function.Strings;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.Hkt.asT2;

public class T2Test {
    @Test
    public void _1() {
        assertThat(T2.of("foo", 42)._1()).isEqualTo("foo");
    }

    @Test
    public void _2() {
        assertThat(T2.of("foo", 42)._2()).isEqualTo(42);
    }

    @Test
    public void of() {
        T2<String, Integer> t2 = T2.of("foo", 42);
        assertThat(t2._1()).isEqualTo("foo");
        assertThat(t2._2()).isEqualTo(42);
    }

    @Test
    public void of$() {
        T2<String, Integer> t2 = T2.of$(() -> "foo", () -> 42);
        assertThat(t2._1()).isEqualTo("foo");
        assertThat(t2._2()).isEqualTo(42);
        // test laziness
        T2.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void narrow() {
        T2<String, Integer> t2 = T2.of("foo", 42);
        __<__<T2.µ, String>, Integer> hkt = t2;
        assertThat(asT2(hkt)).isSameAs(t2);
    }

    @Test
    public void bimap() {
        T2<String, Integer> t2 = T2.of("foo", 42);
        assertThat(t2.bimap(String::length, x -> x + 1)).isEqualTo(T2.of(3, 43));
    }

    @Test
    public void bimap$() {
        T2<String, Integer> t2 = T2.of("foo", 42);
        assertThat(t2.bimap$(String::length, x -> x + 1)).isEqualTo(T2.of(3, 43));
        //test laziness
        T2.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }).bimap$(s -> {
            throw new RuntimeException();
        }, i -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void map_1() {
        T2<String, Integer> t2 = T2.of("foo", 42);
        assertThat(t2.map_1(String::length)).isEqualTo(T2.of(3, 42));
    }

    @Test
    public void map_1$() {
        T2<String, Integer> t2 = T2.of("foo", 42);
        assertThat(t2.map_1$(String::length)).isEqualTo(T2.of(3, 42));
        //test laziness
        T2.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }).map_1$(s -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void map_2() {
        T2<String, Integer> t2 = T2.of("foo", 42);
        assertThat(t2.map_2(x -> x + 1)).isEqualTo(T2.of("foo", 43));
    }

    @Test
    public void map_2$() {
        T2<String, Integer> t2 = T2.of("foo", 42);
        assertThat(t2.map_2$(x -> x + 1)).isEqualTo(T2.of("foo", 43));
        //test laziness
        T2.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }).map_2$(i -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void cata() {
        T2<String, Integer> t2 = T2.of("foo", 42);
        assertThat(t2.<String>cata((s, i) -> s + ":" + i)).isEqualTo("foo:42");
    }

    @Test
    public void swap() {
        T2<String, Integer> t2 = T2.of("foo", 42);
        assertThat(t2.swap()).isEqualTo(T2.of(42, "foo"));
    }

    @Test
    public void swap$() {
        T2<String, Integer> t2 = T2.of("foo", 42);
        assertThat(t2.swap$()).isEqualTo(T2.of(42, "foo"));
        //test laziness
        T2.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }).swap$();
    }

    @Test
    public void merge() {
        T2<String, Integer> t2A = T2.of("foo", 42);
        T2<String, Integer> t2B = T2.of("bar", 8);
        assertThat(T2.merge(t2A, t2B, (s1, s2) -> s1 + s2, (i1, i2) -> i1 + i2))
            .isEqualTo(T2.of("foobar", 50));
    }

    @Test
    public void merge$() {
        T2<String, Integer> t2A = T2.of("foo", 42);
        T2<String, Integer> t2B = T2.of("bar", 8);
        assertThat(T2.merge$(t2A, t2B, (s1, s2) -> s1 + s2, (i1, i2) -> i1 + i2))
            .isEqualTo(T2.of("foobar", 50));
        // test laziness
        T2.merge$(T2.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }), T2.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }), (a, b) -> {
            throw new RuntimeException();
        }, (c, d) -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void toHList() {
        T2<String, Integer> t2 = T2.of("foo", 42);
        HList.HCons<String, HList.HCons<Integer, HList.HNil>> hlist = t2.toHList();
        assertThat(hlist.head()).isEqualTo("foo");
        assertThat(hlist.tail().head()).isEqualTo(42);
        assertThat(hlist.tail().tail()).isEqualTo(HList.nil());
    }

    @Test
    public void toMapEntry() {
        Map.Entry<String, Integer> entry = T2.of("foo", 42).toMapEntry();
        assertThat(entry.getKey()).isEqualTo("foo");
        assertThat(entry.getValue()).isEqualTo(42);
    }

    @Test
    public void fromMapEntry() {
        Map<String, Integer> map = new HashMap<>();
        map.put("foo", 42);
        T2<String, Integer> t2 = T2.fromMapEntry(map.entrySet().iterator().next());
        assertThat(t2).isEqualTo(T2.of("foo", 42));
    }

    @Test
    public void equals() {
        T2<String, Integer> a1 = T2.of("a", 1);
        T2<String, Integer> a2 = T2.of$(() -> "a", () -> 2);
        T2<String, Integer> b1 = T2.of("b", 1);
        assertThat(a2.equals(a2)).isTrue();
        assertThat(a2.equals(T2.of("a", 2))).isTrue();
        assertThat(a1.equals(a2)).isFalse();
        assertThat(a2.equals(a1)).isFalse();
        assertThat(a1.equals(b1)).isFalse();
        assertThat(a2.equals(b1)).isFalse();
        assertThat(b1.equals(a2)).isFalse();
    }

    @Test
    public void testToString() {
        assertThat(T2.of("a", 1).toString()).isEqualTo("(a,1)");
        assertThat(T2.of$(() -> "a", () -> 2).toString()).isEqualTo("(a,2)");
    }

    @Test
    public void eq() {
        Eq<T2<String, Integer>> eq = T2.eq(Eq.fromEquals(), Eq.fromEquals());
        T2<String, Integer> a1 = T2.of("a", 1);
        T2<String, Integer> a2 = T2.of$(() -> "a", () -> 2);
        T2<String, Integer> b1 = T2.of("b", 1);
        assertThat(eq.eq(a2, a2)).isTrue();
        assertThat(eq.eq(a2, T2.of("a", 2))).isTrue();
        assertThat(eq.eq(a1, a2)).isFalse();
        assertThat(eq.eq(a2, a1)).isFalse();
        assertThat(eq.eq(a1, b1)).isFalse();
        assertThat(eq.eq(a2, b1)).isFalse();
        assertThat(eq.eq(b1, a2)).isFalse();
    }

    @Test
    public void ord() {
        Ord<T2<String, Integer>> ord = T2.ord(Ord.fromComparable(), Ord.fromComparable());
        T2<String, Integer> a1 = T2.of("a", 1);
        T2<String, Integer> a2 = T2.of$(() -> "a", () -> 2);
        T2<String, Integer> b1 = T2.of("b", 1);
        assertThat(ord.cmp(a2, a2)).isEqualTo(Ordering.EQ);
        assertThat(ord.cmp(a2, T2.of("a", 2))).isEqualTo(Ordering.EQ);
        assertThat(ord.cmp(a1, a2)).isEqualTo(Ordering.LT);
        assertThat(ord.cmp(a2, a1)).isEqualTo(Ordering.GT);
        assertThat(ord.cmp(a1, b1)).isEqualTo(Ordering.LT);
        assertThat(ord.cmp(a2, b1)).isEqualTo(Ordering.LT);
        assertThat(ord.cmp(b1, a2)).isEqualTo(Ordering.GT);
    }

    @Test
    public void semigroup() {
        T2<String, Integer> t2A = T2.of("foo", 42);
        T2<String, Integer> t2B = T2.of("bar", 8);
        T2<String, Integer> result = T2.semigroup(Strings.monoid, Integers.additiveGroup).apply(t2A, t2B);
        assertThat(result).isEqualTo(T2.of("foobar", 50));
    }

    @Test
    public void monoid() {
        T2<String, Integer> t2A = T2.of("foo", 42);
        T2<String, Integer> t2B = T2.of("bar", 8);
        T2Monoid<String, Integer> monoid = T2.monoid(Strings.monoid, Integers.additiveGroup);
        T2<String, Integer> result = monoid.apply(t2A, t2B);
        assertThat(result).isEqualTo(T2.of("foobar", 50));
        T2<String, Integer> identity = monoid.identity();
        assertThat(identity).isEqualTo(T2.of("", 0));
    }

    @Test
    public void group() {
        T2<BigInteger, Integer> t2A = T2.of(BigInteger.TEN, 42);
        T2<BigInteger, Integer> t2B = T2.of(BigInteger.ONE, 8);
        T2Group<BigInteger, Integer> group = T2.group(BigIntegers.additiveGroup, Integers.additiveGroup);
        T2<BigInteger, Integer> result = group.apply(t2A, t2B);
        assertThat(result).isEqualTo(T2.of(BigInteger.valueOf(11), 50));
        T2<BigInteger, Integer> identity = group.identity();
        assertThat(identity).isEqualTo(T2.of(BigInteger.ZERO, 0));
        T2<BigInteger, Integer> inverse = group.inverse(t2A);
        assertThat(inverse).isEqualTo(T2.of(BigInteger.valueOf(-10), -42));
    }

    @Test
    public void functor() {
        T2<Object, Integer> t2 = T2.functor().map(x -> x + 1, T2.of("foo", 42));
        assertThat(t2).isEqualTo(T2.of("foo", 43));
    }

    @Test
    public void apply() {
        T2<String, Object> t2 = T2.apply(Strings.monoid).ap(T2.of("foo", x -> x + 1), T2.of("bar", 42));
        assertThat(t2).isEqualTo(T2.of("foobar", 43));
    }

    @Test
    public void applicative() {
        T2<String, Integer> t2 = T2.applicative(Strings.monoid).pure(42);
        assertThat(t2).isEqualTo(T2.of("", 42));
    }

    @Test
    public void bind() {
        T2<String, Integer> t2 = T2.bind(Strings.monoid).bind(T2.of("foo", 42), x -> T2.of("bar", x + 1));
        assertThat(t2).isEqualTo(T2.of("foobar", 43));
    }

    @Test
    public void monad() {
        // has no new methods
    }

    @Test
    public void monadRec() {
        Function<Integer, __<__<T2.µ, String>, Either<Integer, Integer>>> fn = i ->
                                                                                   i < 10 ? T2.of(i + "-", Either.Left(i + 1)) : T2.of(i + "", Either.Right(i));
        T2<String, Integer> t2 = T2.monadRec(Strings.monoid).tailRec(fn, 1);
        assertThat(t2).isEqualTo(T2.of("1-2-3-4-5-6-7-8-9-10", 10));
    }

    @Test
    public void comonad() {
        T2Comonad<String> comonad = T2.comonad();
        assertThat(comonad.extract(T2.of("foo", 42))).isEqualTo(42);
        assertThat(comonad.duplicate(T2.of("foo", 42))).isEqualTo(T2.of("foo", T2.of("foo", 42)));
    }

    @Test
    public void bifunctor() {
        T2<Integer, Integer> bimap = T2.bifunctor.bimap(String::length, x -> x + 1, T2.of("foo", 42));
        assertThat(bimap).isEqualTo(T2.of(3, 43));
        T2<Integer, Integer> first = T2.bifunctor.first(String::length, T2.of("foo", 42));
        assertThat(first).isEqualTo(T2.of(3, 42));
        T2<String, Integer> second = T2.bifunctor.second(x -> x + 1, T2.of("foo", 42));
        assertThat(second).isEqualTo(T2.of("foo", 43));
    }

    @Test
    public void biapply() {
        T2<Integer, Integer> biapply = T2.biapply.biapply(T2.of(String::length, x -> x + 1), T2.of("foo", 42));
        assertThat(biapply).isEqualTo(T2.of(3, 43));
    }

    @Test
    public void biapplicative() {
        T2<String, Integer> t2 = T2.biapplicative.bipure("foo", 42);
        assertThat(t2).isEqualTo(T2.of("foo", 42));
    }
}