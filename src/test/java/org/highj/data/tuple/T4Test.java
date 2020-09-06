package org.highj.data.tuple;

import org.highj.data.HList;
import org.highj.data.HList.HCons;
import org.highj.data.HList.HNil;
import org.highj.data.eq.Eq;
import org.highj.data.num.Integers;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.data.tuple.t4.T4Group;
import org.highj.data.tuple.t4.T4Monoid;
import org.highj.data.tuple.t4.T4Semigroup;
import org.highj.function.Strings;
import org.highj.typeclass0.group.Semigroup;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.Hkt.asT4;

public class T4Test {

    @Test
    public void _1() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4._1()).isEqualTo(4711L);
    }

    @Test
    public void _2() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4._2()).isEqualTo("foo");
    }

    @Test
    public void _3() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4._3()).isEqualTo(42);
    }

    @Test
    public void _4() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4._4()).isEqualTo('A');
    }

    @Test
    public void of() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4._1()).isEqualTo(4711L);
        assertThat(t4._2()).isEqualTo("foo");
        assertThat(t4._3()).isEqualTo(42);
        assertThat(t4._4()).isEqualTo('A');
    }

    @Test
    public void of$() {
        T4<Long, String, Integer, Character> t4 =
            T4.of$(() -> 4711L, () -> "foo", () -> 42, () -> 'A');
        assertThat(t4._1()).isEqualTo(4711L);
        assertThat(t4._2()).isEqualTo("foo");
        assertThat(t4._3()).isEqualTo(42);
        assertThat(t4._4()).isEqualTo('A');
        // test laziness
        T4.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void quadmap() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        T4<Long, Integer, Integer, Integer> quadmap = t4.quadmap(
            l -> l - 11, String::length, x -> x + 1, c -> (int) c);
        assertThat(quadmap).isEqualTo(T4.of(4700L, 3, 43, 65));
    }

    @Test
    public void quadmap$() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        T4<Long, Integer, Integer, Integer> quadmap = t4.quadmap$(
            l -> l - 11, String::length, x -> x + 1, c -> (int) c);
        assertThat(quadmap).isEqualTo(T4.of(4700L, 3, 43, 65));
        // test laziness
        T4.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }).quadmap$(l -> {
            throw new RuntimeException();
        }, s -> {
            throw new RuntimeException();
        }, i -> {
            throw new RuntimeException();
        }, c -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void narrow() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(asT4(t4)).isSameAs(t4);
        T4<Long, String, Integer, Character> t4$ = T4.of$(() -> 4711L, () -> "foo", () -> 42, () -> 'A');
        assertThat(asT4(t4$)).isSameAs(t4$);
    }

    @Test
    public void map_1() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4.map_1(x -> x - 11)).isEqualTo(T4.of(4700L, "foo", 42, 'A'));
    }

    @Test
    public void map_1$() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4.map_1$(x -> x - 11)).isEqualTo(T4.of(4700L, "foo", 42, 'A'));
        // test laziness
        T4.of$(() -> {
            throw new RuntimeException();
        }, () -> {
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
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4.map_2(String::length)).isEqualTo(T4.of(4711L, 3, 42, 'A'));
    }

    @Test
    public void map_2$() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4.map_2(String::length)).isEqualTo(T4.of(4711L, 3, 42, 'A'));
        // test laziness
        T4.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }).map_2$(s -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void map_3() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4.map_3(x -> x + 1)).isEqualTo(T4.of(4711L, "foo", 43, 'A'));
    }

    @Test
    public void map_3$() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4.map_3$(x -> x + 1)).isEqualTo(T4.of(4711L, "foo", 43, 'A'));
        // test laziness
        T4.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }).map_3$(i -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void map_4() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4.map_4(c -> (int) c)).isEqualTo(T4.of(4711L, "foo", 42, 65));
    }

    @Test
    public void map_4$() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4.map_4$(c -> (int) c)).isEqualTo(T4.of(4711L, "foo", 42, 65));
        // test laziness
        T4.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }).map_4$(c -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void cata() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4.<String>cata((l, s, i, c) -> l + s + i + c)).isEqualTo("4711foo42A");
    }

    @Test
    public void equals() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4.equals(T4.of(4711L, "foo", 42, 'A'))).isTrue();
        assertThat(t4.equals(T4.of$(() -> 4711L, () -> "foo", () -> 42, () -> 'A'))).isTrue();
        assertThat(t4.equals(T4.of(4715L, "bar", 42, 'A'))).isFalse();
        assertThat(t4.equals(T4.of(4711L, "bar", 42, 'A'))).isFalse();
        assertThat(t4.equals(T4.of(4711L, "foo", 43, 'A'))).isFalse();
        assertThat(t4.equals(T4.of(4711L, "foo", 42, 'B'))).isFalse();
    }

    @Test
    public void merge() {
        T4<Long, String, Integer, Character> t4a = T4.of(4700L, "foo", 42, 'A');
        T4<Long, String, Integer, Character> t4b = T4.of(11L, "bar", 8, 'B');
        assertThat(T4.merge(t4a, t4b, Long::sum, (s1, s2) -> s1 + s2, Integer::sum, (c1, c2) -> "" + c1 + c2))
            .isEqualTo(T4.of(4711L, "foobar", 50, "AB"));
    }

    @Test
    public void merge$() {
        T4<Long, String, Integer, Character> t4a = T4.of(4700L, "foo", 42, 'A');
        T4<Long, String, Integer, Character> t4b = T4.of(11L, "bar", 8, 'B');
        assertThat(T4.merge$(t4a, t4b, Long::sum, (s1, s2) -> s1 + s2, Integer::sum, (c1, c2) -> "" + c1 + c2))
            .isEqualTo(T4.of(4711L, "foobar", 50, "AB"));
        //test laziness
        T4<Long, String, Integer, Character> ex = T4.of$(() -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        }, () -> {
            throw new RuntimeException();
        });
        T4.merge$(ex, ex, (l1, l2) -> {
            throw new RuntimeException();
        }, (s1, s2) -> {
            throw new RuntimeException();
        }, (i1, i2) -> {
            throw new RuntimeException();
        }, (c1, c2) -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void eq() {
        Eq<T4<Long, String, Integer, Character>> eq = T4.eq(Eq.fromEquals(), Eq.fromEquals(), Eq.fromEquals(), Eq.fromEquals());
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(eq.eq(t4, T4.of(4711L, "foo", 42, 'A'))).isTrue();
        assertThat(eq.eq(t4, T4.of$(() -> 4711L, () -> "foo", () -> 42, () -> 'A'))).isTrue();
        assertThat(eq.eq(t4, T4.of(4712L, "foo", 42, 'A'))).isFalse();
        assertThat(eq.eq(t4, T4.of(4711L, "bar", 42, 'A'))).isFalse();
        assertThat(eq.eq(t4, T4.of(4711L, "foo", 43, 'A'))).isFalse();
        assertThat(eq.eq(t4, T4.of(4711L, "foo", 42, 'B'))).isFalse();
    }

    @Test
    public void ord() {
        Ord<T4<Long, String, Integer, Character>> ord = T4.ord(Ord.fromComparable(), Ord.fromComparable(), Ord.fromComparable(), Ord.fromComparable());
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "bar", 42, 'A');
        assertThat(ord.cmp(t4, T4.of(4711L, "bar", 42, 'A'))).isEqualTo(Ordering.EQ);
        assertThat(ord.cmp(t4, T4.of$(() -> 4711L, () -> "bar", () -> 42, () -> 'A'))).isEqualTo(Ordering.EQ);
        assertThat(ord.cmp(t4, T4.of(4712L, "bar", 42, 'A'))).isEqualTo(Ordering.LT);
        assertThat(ord.cmp(t4, T4.of(4711L, "foo", 42, 'A'))).isEqualTo(Ordering.LT);
        assertThat(ord.cmp(t4, T4.of(4711L, "bar", 43, 'A'))).isEqualTo(Ordering.LT);
        assertThat(ord.cmp(t4, T4.of(4711L, "bar", 42, 'B'))).isEqualTo(Ordering.LT);
        assertThat(ord.cmp(T4.of(4712L, "bar", 42, 'A'), t4)).isEqualTo(Ordering.GT);
        assertThat(ord.cmp(T4.of(4711L, "foo", 42, 'A'), t4)).isEqualTo(Ordering.GT);
        assertThat(ord.cmp(T4.of(4711L, "bar", 43, 'A'), t4)).isEqualTo(Ordering.GT);
        assertThat(ord.cmp(T4.of(4711L, "bar", 42, 'B'), t4)).isEqualTo(Ordering.GT);
    }

    @Test
    public void testToString() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(t4.toString()).isEqualTo("(4711,foo,42,A)");
    }

    @Test
    public void functor() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(T4.<Long, String, Integer>functor().map(c -> (int) c, t4)).isEqualTo(T4.of(4711L, "foo", 42, 65));
    }

    @Test
    public void apply() {
        T4<Long, String, Integer, Character> t4 = T4.of(4700L, "bar", 42, 'A');
        T4<Long, String, Integer, Function<Character, Character>> fn = T4.of(11L, "foo", 8, c -> (char) (c + 1));
        Semigroup<Long> longSemigroup = Long::sum;
        T4<Long, String, Integer, Character> ap = T4.apply(longSemigroup, Strings.monoid, Integers.additiveGroup).ap(fn, t4);
        assertThat(ap).isEqualTo(T4.of(4711L, "foobar", 50, 'B'));
    }

    @Test
    public void applicative() {
        T4<Integer, String, Integer, Character> pure = T4.applicative(Integers.multiplicativeMonoid, Strings.monoid, Integers.additiveGroup).pure('A');
        assertThat(pure).isEqualTo(T4.of(1, "", 0, 'A'));
    }

    @Test
    public void bind() {
        T4<Integer, String, Integer, Character> t4 = T4.of(47, "foo", 42, 'A');
        T4<Integer, String, Integer, Character> bind = T4.monad(Integers.multiplicativeMonoid, Strings.monoid, Integers.additiveGroup)
                                                         .bind(t4, c -> T4.of((int) c, "" + c, (int) c, c));
        assertThat(bind).isEqualTo(T4.of(47 * 65, "fooA", 42 + 65, 'A'));
    }

    @Test
    public void monad() {
        // no new methods
    }

    @Test
    public void comonad() {
        assertThat(T4.comonad().extract(T4.of(4711L, "foo", 42, 'A'))).isEqualTo('A');
        assertThat(T4.comonad().duplicate(T4.of(4711L, "foo", 42, 'A')))
            .isEqualTo(T4.of(4711L, "foo", 42, T4.of(4711L, "foo", 42, 'A')));
    }

    @Test
    public void semigroup() {
        T4Semigroup<Integer, String, Integer, Integer> semigroup = T4.semigroup(Integers.maxMonoid, Strings.monoid,
            Integers.additiveGroup, Integers.multiplicativeMonoid);
        assertThat(semigroup.apply(T4.of(10, "foo", 5, 5), T4.of(5, "bar", 2, 2))).isEqualTo(T4.of(10, "foobar", 7, 10));
    }

    @Test
    public void monoid() {
        T4Monoid<Integer, String, Integer, Integer> monoid = T4.monoid(Integers.maxMonoid, Strings.monoid,
            Integers.additiveGroup, Integers.multiplicativeMonoid);
        assertThat(monoid.apply(T4.of(10, "foo", 5, 5), T4.of(5, "bar", 2, 2))).isEqualTo(T4.of(10, "foobar", 7, 10));
        assertThat(monoid.identity()).isEqualTo(T4.of(Integer.MIN_VALUE, "", 0, 1));
    }

    @Test
    public void group() {
        T4Group<Integer, Integer, Integer, Integer> group = T4.group(Integers.additiveGroup, Integers.additiveGroup,
            Integers.additiveGroup, Integers.additiveGroup);
        assertThat(group.apply(T4.of(10, 4, 5, 5), T4.of(12, 0, 2, 2))).isEqualTo(T4.of(22, 4, 7, 7));
        assertThat(group.identity()).isEqualTo(T4.of(0, 0, 0, 0));
        assertThat(group.inverse(T4.of(10, -3, 2, 2))).isEqualTo(T4.of(-10, 3, -2, -2));
    }

    @Test
    public void bifunctor() {
        T4<Long, String, Integer, Character> t4 = T4.of(4711L, "foo", 42, 'A');
        assertThat(T4.<Long, String>bifunctor().bimap(i -> i + 8, c -> (int) c, t4)).isEqualTo(T4.of(4711L, "foo", 50, 65));
        assertThat(T4.<Long, String>bifunctor().first(i -> i + 8, t4)).isEqualTo(T4.of(4711L, "foo", 50, 'A'));
        assertThat(T4.<Long, String>bifunctor().second(c -> (int) c, t4)).isEqualTo(T4.of(4711L, "foo", 42, 65));
    }

    @Test
    public void biapply() {
        T4<Integer, String, Integer, Character> t4 = T4.of(4700, "bar", 42, 'A');
        T4<Integer, String, Function<Integer, Integer>, Function<Character, String>> fn =
            T4.of(11, "foo", i -> i + 8, c -> c + "!");
        T4<Integer, String, Integer, String> biapply = T4.biapply(Integers.additiveGroup, Strings.monoid).biapply(fn, t4);
        assertThat(biapply).isEqualTo(T4.of(4711, "foobar", 50, "A!"));
    }

    @Test
    public void biapplicative() {
        T4<Integer, String, Integer, Character> t4 = T4.biapplicative(Integers.additiveGroup, Strings.monoid).bipure(42, 'A');
        assertThat(t4).isEqualTo(T4.of(0, "", 42, 'A'));
    }

    @Test
    public void toHList() {
        HCons<Long, HCons<String, HCons<Integer, HCons<Character, HNil>>>> hList = T4.of(4711L, "foo", 42, 'A').toHList();
        assertThat(hList.head()).isEqualTo(4711L);
        assertThat(hList.tail().head()).isEqualTo("foo");
        assertThat(hList.tail().tail().head()).isEqualTo(42);
        assertThat(hList.tail().tail().tail().head()).isEqualTo('A');
        assertThat(hList.tail().tail().tail().tail()).isEqualTo(HList.nil());
    }
}