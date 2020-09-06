package org.highj.data.tuple;

import org.derive4j.hkt.__;
import org.highj.data.HList;
import org.highj.data.eq.Eq;
import org.highj.data.num.BigIntegers;
import org.highj.data.num.Integers;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.data.tuple.t3.T3Group;
import org.highj.data.tuple.t3.T3Monoid;
import org.highj.data.tuple.t3.T3Semigroup;
import org.highj.function.Strings;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.Hkt.asT3;

public class T3Test {

    @Test
    public void _1() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3._1()).isEqualTo("foo");
    }

    @Test
    public void _2() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3._2()).isEqualTo(42);
    }

    @Test
    public void _3() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3._3()).isEqualTo('A');
    }

    @Test
    public void of() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3._1()).isEqualTo("foo");
        assertThat(t3._2()).isEqualTo(42);
        assertThat(t3._3()).isEqualTo('A');
    }

    @Test
    public void of$() {
        T3<String, Integer, Character> t3 = T3.of$(() -> "foo", () -> 42, () -> 'A');
        assertThat(t3._1()).isEqualTo("foo");
        assertThat(t3._2()).isEqualTo(42);
        assertThat(t3._3()).isEqualTo('A');
        // test laziness
        T3.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void trimap() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        T3<Integer, Integer, Integer> trimap = t3.trimap(String::length, x -> x + 1, c -> (int) c);
        assertThat(trimap).isEqualTo(T3.of(3, 43, 65));
    }

    @Test
    public void trimap$() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        T3<Integer, Integer, Integer> trimap = t3.trimap$(String::length, x -> x + 1, c -> (int) c);
        assertThat(trimap).isEqualTo(T3.of(3, 43, 65));
        // test laziness
        T3.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }).trimap$(s -> {
            throw new RuntimeException();
        }, i -> {
            throw new RuntimeException();
        }, c -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void narrow() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        __<__<__<T3.µ, String>, Integer>, Character> hkt = t3;
        assertThat(asT3(hkt)).isSameAs(t3);
        T3<String, Integer, Character> t3$ = T3.of$(() -> "foo", () -> 42, () -> 'A');
        __<__<__<T3.µ, String>, Integer>, Character> hkt$ = t3$;
        assertThat(asT3(hkt$)).isSameAs(t3$);
    }

    @Test
    public void map_1() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3.map_1(String::length)).isEqualTo(T3.of(3, 42, 'A'));
    }

    @Test
    public void map_1$() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3.map_1(String::length)).isEqualTo(T3.of(3, 42, 'A'));
        // test laziness
        T3.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }).map_1$(s -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void map_2() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3.map_2(x -> x + 1)).isEqualTo(T3.of("foo", 43, 'A'));
    }

    @Test
    public void map_2$() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3.map_2$(x -> x + 1)).isEqualTo(T3.of("foo", 43, 'A'));
        // test laziness
        T3.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }).map_2$(i -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void map_3() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3.map_3(c -> (int) c)).isEqualTo(T3.of("foo", 42, 65));
    }

    @Test
    public void map_3$() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3.map_3$(c -> (int) c)).isEqualTo(T3.of("foo", 42, 65));
        // test laziness
        T3.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }).map_3$(c -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void cata() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3.<String>cata((s, i, c) -> s + i + c)).isEqualTo("foo42A");
    }

    @Test
    public void equals() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3.equals(T3.of("foo", 42, 'A'))).isTrue();
        assertThat(t3.equals(T3.of$(() -> "foo", () -> 42, () -> 'A'))).isTrue();
        assertThat(t3.equals(T3.of("bar", 42, 'A'))).isFalse();
        assertThat(t3.equals(T3.of("foo", 43, 'A'))).isFalse();
        assertThat(t3.equals(T3.of("foo", 42, 'B'))).isFalse();
    }

    @Test
    public void merge() {
        T3<String, Integer, Character> t3a = T3.of("foo", 42, 'A');
        T3<String, Integer, Character> t3b = T3.of("bar", 8, 'B');
        assertThat(T3.merge(t3a, t3b, (s1, s2) -> s1 + s2, (i1, i2) -> i1 + i2, (c1, c2) -> "" + c1 + c2))
            .isEqualTo(T3.of("foobar", 50, "AB"));
    }

    @Test
    public void merge$() {
        T3<String, Integer, Character> t3a = T3.of("foo", 42, 'A');
        T3<String, Integer, Character> t3b = T3.of("bar", 8, 'B');
        assertThat(T3.merge$(t3a, t3b, (s1, s2) -> s1 + s2, (i1, i2) -> i1 + i2, (c1, c2) -> "" + c1 + c2))
            .isEqualTo(T3.of("foobar", 50, "AB"));
        //test laziness
        T3<String, Integer, Character> ex = T3.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        });
        T3.merge$(ex, ex, (s1, s2) -> {
            throw new RuntimeException();
        }, (i1, i2) -> {
            throw new RuntimeException();
        }, (c1, c2) -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void eq() {
        Eq<T3<String, Integer, Character>> eq = T3.eq(Eq.fromEquals(), Eq.fromEquals(), Eq.fromEquals());
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(eq.eq(t3, T3.of("foo", 42, 'A'))).isTrue();
        assertThat(eq.eq(t3, T3.of$(() -> "foo", () -> 42, () -> 'A'))).isTrue();
        assertThat(eq.eq(t3, T3.of("bar", 42, 'A'))).isFalse();
        assertThat(eq.eq(t3, T3.of("foo", 43, 'A'))).isFalse();
        assertThat(eq.eq(t3, T3.of("foo", 42, 'B'))).isFalse();
    }

    @Test
    public void ord() {
        Ord<T3<String, Integer, Character>> ord = T3.ord(Ord.fromComparable(), Ord.fromComparable(), Ord.fromComparable());
        T3<String, Integer, Character> t3 = T3.of("bar", 42, 'A');
        assertThat(ord.cmp(t3, T3.of("bar", 42, 'A'))).isEqualTo(Ordering.EQ);
        assertThat(ord.cmp(t3, T3.of$(() -> "bar", () -> 42, () -> 'A'))).isEqualTo(Ordering.EQ);
        assertThat(ord.cmp(t3, T3.of("foo", 42, 'A'))).isEqualTo(Ordering.LT);
        assertThat(ord.cmp(t3, T3.of("bar", 43, 'A'))).isEqualTo(Ordering.LT);
        assertThat(ord.cmp(t3, T3.of("bar", 42, 'B'))).isEqualTo(Ordering.LT);
        assertThat(ord.cmp(T3.of("foo", 42, 'A'), t3)).isEqualTo(Ordering.GT);
        assertThat(ord.cmp(T3.of("bar", 43, 'A'), t3)).isEqualTo(Ordering.GT);
        assertThat(ord.cmp(T3.of("bar", 42, 'B'), t3)).isEqualTo(Ordering.GT);
    }

    @Test
    public void testToString() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3.toString()).isEqualTo("(foo,42,A)");
    }

    @Test
    public void functor() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(T3.<String, Integer>functor().map(c -> (int) c, t3)).isEqualTo(T3.of("foo", 42, 65));
    }

    @Test
    public void apply() {
        T3<String, Integer, Character> t3 = T3.of("bar", 42, 'A');
        T3<String, Integer, Function<Character, Character>> fn = T3.of("foo", 8, c -> (char) (c + 1));
        T3<String, Integer, Character> ap = T3.apply(Strings.monoid, Integers.additiveGroup).ap(fn, t3);
        assertThat(ap).isEqualTo(T3.of("foobar", 50, 'B'));
    }

    @Test
    public void applicative() {
        T3<String, Integer, Character> pure = T3.applicative(Strings.monoid, Integers.additiveGroup).pure('A');
        assertThat(pure).isEqualTo(T3.of("", 0, 'A'));
    }

    @Test
    public void bind() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        T3<String, Integer, Character> bind = T3.monad(Strings.monoid, Integers.additiveGroup).bind(t3, c -> T3.of("" + c, (int) c, c));
        assertThat(bind).isEqualTo(T3.of("fooA", 42 + 65, 'A'));
    }

    @Test
    public void monad() {
        // no new methods
    }

    @Test
    public void comonad() {
        assertThat(T3.comonad().extract(T3.of("foo", 42, 'A'))).isEqualTo('A');
        assertThat(T3.comonad().duplicate(T3.of("foo", 42, 'A')))
            .isEqualTo(T3.of("foo", 42, T3.of("foo", 42, 'A')));
    }

    @Test
    public void semigroup() {
        T3Semigroup<String, Integer, Integer> semigroup = T3.semigroup(Strings.monoid, Integers.additiveGroup, Integers.multiplicativeMonoid);
        assertThat(semigroup.apply(T3.of("foo", 5, 5), T3.of("bar", 2, 2))).isEqualTo(T3.of("foobar", 7, 10));
    }

    @Test
    public void monoid() {
        T3Monoid<String, Integer, Integer> monoid = T3.monoid(Strings.monoid, Integers.additiveGroup, Integers.multiplicativeMonoid);
        assertThat(monoid.apply(T3.of("foo", 5, 5), T3.of("bar", 2, 2))).isEqualTo(T3.of("foobar", 7, 10));
        assertThat(monoid.identity()).isEqualTo(T3.of("", 0, 1));
    }

    @Test
    public void group() {
        T3Group<BigInteger, Integer, Integer> group = T3.group(BigIntegers.additiveGroup, Integers.additiveGroup, Integers.additiveGroup);
        assertThat(group.apply(T3.of(BigInteger.ONE, 5, 5), T3.of(BigInteger.TEN, 2, 2))).isEqualTo(T3.of(BigInteger.valueOf(11), 7, 7));
        assertThat(group.identity()).isEqualTo(T3.of(BigInteger.ZERO, 0, 0));
        assertThat(group.inverse(T3.of(BigInteger.TEN, 2, 2))).isEqualTo(T3.of(BigInteger.valueOf(-10), -2, -2));
    }

    @Test
    public void bifunctor() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(T3.<String>bifunctor().bimap(i -> i + 8, c -> (int) c, t3)).isEqualTo(T3.of("foo", 50, 65));
        assertThat(T3.<String>bifunctor().first(i -> i + 8, t3)).isEqualTo(T3.of("foo", 50, 'A'));
        assertThat(T3.<String>bifunctor().second(c -> (int) c, t3)).isEqualTo(T3.of("foo", 42, 65));
    }

    @Test
    public void biapply() {
        T3<String, Integer, Character> t3 = T3.of("bar", 42, 'A');
        T3<String, Function<Integer, Integer>, Function<Character, String>> fn =
            T3.of("foo", i -> i + 8, c -> c + "!");
        T3<String, Integer, String> biapply = T3.biapply(Strings.monoid).biapply(fn, t3);
        assertThat(biapply).isEqualTo(T3.of("foobar", 50, "A!"));
    }

    @Test
    public void biapplicative() {
        T3<String, Integer, Character> t3 = T3.biapplicative(Strings.monoid).bipure(42, 'A');
        assertThat(t3).isEqualTo(T3.of("", 42, 'A'));
    }

    @Test
    public void toHList() {
        HList.HCons<String, HList.HCons<Integer, HList.HCons<Character, HList.HNil>>> hList = T3.of("foo", 42, 'A').toHList();
        assertThat(hList.head()).isEqualTo("foo");
        assertThat(hList.tail().head()).isEqualTo(42);
        assertThat(hList.tail().tail().head()).isEqualTo('A');
        assertThat(hList.tail().tail().tail()).isEqualTo(HList.nil());
    }
}