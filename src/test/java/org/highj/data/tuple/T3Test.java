package org.highj.data.tuple;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

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
        assertThat(T3.narrow(hkt)).isSameAs(t3);
        T3<String, Integer, Character> t3$ = T3.of$(() -> "foo", () -> 42, () -> 'A');
        __<__<__<T3.µ, String>, Integer>, Character> hkt$ = t3$;
        assertThat(T3.narrow(hkt$)).isSameAs(t3$);
    }

    @Test
    public void map_1() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3.map_1(String::length)).isEqualTo(T3.of(3,42,'A'));
    }

    @Test
    public void map_1$() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3.map_1(String::length)).isEqualTo(T3.of(3,42,'A'));
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
        assertThat(t3.map_2(x -> x + 1)).isEqualTo(T3.of("foo",43,'A'));
    }

    @Test
    public void map_2$() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3.map_2$(x -> x + 1)).isEqualTo(T3.of("foo",43,'A'));
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
        assertThat(t3.map_3(c -> (int) c)).isEqualTo(T3.of("foo",42,65));
    }

    @Test
    public void map_3$() {
        T3<String, Integer, Character> t3 = T3.of("foo", 42, 'A');
        assertThat(t3.map_3$(c -> (int) c)).isEqualTo(T3.of("foo",42,65));
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
        assertThat(t3.<String>cata((s,i,c) -> s + i + c)).isEqualTo("foo42A");
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
        assertThat(T3.merge(t3a, t3b, (s1,s2) -> s1+s2, (i1,i2) -> i1 + i2, (c1,c2)-> ""+ c1 + c2))
                .isEqualTo(T3.of("foobar", 50, "AB"));
    }

    @Test
    public void merge$() {
        T3<String, Integer, Character> t3a = T3.of("foo", 42, 'A');
        T3<String, Integer, Character> t3b = T3.of("bar", 8, 'B');
        assertThat(T3.merge$(t3a, t3b, (s1,s2) -> s1+s2, (i1,i2) -> i1 + i2, (c1,c2)-> ""+ c1 + c2))
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

    }

    @Test
    public void testToString() {

    }

    @Test
    public void functor() {

    }

    @Test
    public void apply() {

    }

    @Test
    public void applicative() {

    }

    @Test
    public void bind() {

    }

    @Test
    public void monad() {

    }

    @Test
    public void comonad() {

    }

    @Test
    public void semigroup() {

    }

    @Test
    public void monoid() {

    }

    @Test
    public void group() {

    }

    @Test
    public void bifunctor() {

    }

    @Test
    public void biapply() {

    }

    @Test
    public void biapplicative() {

    }

    @Test
    public void toHList() {

    }
}