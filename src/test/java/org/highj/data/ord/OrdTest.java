package org.highj.data.ord;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.eq.Eq;
import org.highj.data.tuple.T2;
import org.highj.function.Strings;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.highj.Hkt.asOrd;

public class OrdTest {

    @Test
    public void compare() {
        Ord<String> ord = Ord.fromComparable();
        assertThat(ord.compare("a", "b")).isLessThan(0);
        assertThat(ord.compare("a", "a")).isEqualTo(0);
        assertThat(ord.compare("b", "a")).isGreaterThan(0);
    }

    @Test
    public void contravariant() {
        Ord<String> lengthy = Ord.contravariant.contramap(String::length, Ord.fromComparable());
        assertThat(lengthy.cmp("one", "two")).isEqualTo(Ordering.EQ);
        assertThat(lengthy.cmp("one", "three")).isEqualTo(Ordering.LT);
        assertThat(lengthy.cmp("three", "four")).isEqualTo(Ordering.GT);
    }

    @Test
    public void decidable() {
        Ord<Integer> evenFirst = Ord.decidable.choose(
            i -> i % 2 == 0 ? Either.Left(i) : Either.Right(i),
            Ord.<Integer>fromComparable(),
            Ord.<Integer>fromComparable());
        //even numbers have normal order
        assertThat(evenFirst.cmp(2, 6)).isEqualTo(Ordering.LT);
        assertThat(evenFirst.cmp(4, 4)).isEqualTo(Ordering.EQ);
        assertThat(evenFirst.cmp(6, 2)).isEqualTo(Ordering.GT);
        //odd numbers have normal order
        assertThat(evenFirst.cmp(1, 5)).isEqualTo(Ordering.LT);
        assertThat(evenFirst.cmp(3, 3)).isEqualTo(Ordering.EQ);
        assertThat(evenFirst.cmp(5, 1)).isEqualTo(Ordering.GT);
        //even numbers are always smaller than odd numbers
        assertThat(evenFirst.cmp(6, 1)).isEqualTo(Ordering.LT);
        assertThat(evenFirst.cmp(1, 6)).isEqualTo(Ordering.GT);

        assertThatThrownBy(() -> Ord.decidable.lose(p -> null))
            .isInstanceOf(AssertionError.class);
    }

    @Test
    public void divisible() {
        Ord<Point> conq = Ord.divisible.conquer();
        assertThat(conq.cmp(new Point(2, 4), new Point(3, 8))).isEqualTo(Ordering.EQ);
        assertThat(conq.cmp(new Point(3, 8), new Point(2, 4))).isEqualTo(Ordering.EQ);

        Ord<Point> div = Ord.divisible.divide(p -> T2.of(p.x, p.y),
            Ord.<Integer>fromComparable(),
            Ord.<Integer>fromComparable());
        assertThat(div.cmp(new Point(1, 8), new Point(8, 1))).isEqualTo(Ordering.LT);
        assertThat(div.cmp(new Point(8, 1), new Point(8, 2))).isEqualTo(Ordering.LT);
        assertThat(div.cmp(new Point(3, 2), new Point(3, 2))).isEqualTo(Ordering.EQ);
        assertThat(div.cmp(new Point(3, 2), new Point(2, 9))).isEqualTo(Ordering.GT);
        assertThat(div.cmp(new Point(3, 2), new Point(3, 1))).isEqualTo(Ordering.GT);
    }

    @Test
    public void eq() {
        Ord<String> ord = Ord.fromComparable();
        assertThat(ord.eq("a", "b")).isFalse();
        assertThat(ord.eq("a", "a")).isTrue();
        assertThat(ord.eq("b", "a")).isFalse();
    }

    @Test
    public void fromComparable() {
        Ord<String> ord = Ord.fromComparable();
        assertThat(ord.cmp("a", "b")).isEqualTo(Ordering.LT);
        assertThat(ord.cmp("a", "a")).isEqualTo(Ordering.EQ);
        assertThat(ord.cmp("b", "a")).isEqualTo(Ordering.GT);
    }

    @Test
    public void fromComparator() {
        Ord<String> ord = Ord.fromComparator(Strings.ordIgnoreCase);
        assertThat(ord.cmp("a", "b")).isEqualTo(Ordering.LT);
        assertThat(ord.cmp("A", "a")).isEqualTo(Ordering.EQ);
        assertThat(ord.cmp("b", "A")).isEqualTo(Ordering.GT);
    }

    @Test
    public void greater() {
        Ord<String> ord = Ord.fromComparable();
        assertThat(ord.greater("a", "b")).isFalse();
        assertThat(ord.greater("a", "a")).isFalse();
        assertThat(ord.greater("b", "a")).isTrue();
    }

    @Test
    public void greaterEqual() {
        Ord<String> ord = Ord.fromComparable();
        assertThat(ord.greaterEqual("a", "b")).isFalse();
        assertThat(ord.greaterEqual("a", "a")).isTrue();
        assertThat(ord.greaterEqual("b", "a")).isTrue();
    }

    @Test
    public void less() {
        Ord<String> ord = Ord.fromComparable();
        assertThat(ord.less("a", "b")).isTrue();
        assertThat(ord.less("a", "a")).isFalse();
        assertThat(ord.less("b", "a")).isFalse();
    }

    @Test
    public void lessEqual() {
        Ord<String> ord = Ord.fromComparable();
        assertThat(ord.lessEqual("a", "b")).isTrue();
        assertThat(ord.lessEqual("a", "a")).isTrue();
        assertThat(ord.lessEqual("b", "a")).isFalse();
    }

    @Test
    public void max() {
        Ord<String> ord = Ord.fromComparable();
        assertThat(ord.max("a", "b")).isEqualTo("b");
        assertThat(ord.max("b", "b")).isEqualTo("b");
        assertThat(ord.max("b", "a")).isEqualTo("b");
    }

    @Test
    public void min() {
        Ord<String> ord = Ord.fromComparable();
        assertThat(ord.min("a", "b")).isEqualTo("a");
        assertThat(ord.min("a", "a")).isEqualTo("a");
        assertThat(ord.min("b", "a")).isEqualTo("a");
    }

    @Test
    public void narrow() {
        Ord<String> ord = Ord.fromComparable();
        __<Ord.µ, String> hkt = ord;
        assertThat(asOrd(hkt)).isSameAs(ord);
    }

    @Test
    public void toEq() {
        Eq<String> eq = Ord.<String>fromComparable().toEq();
        assertThat(eq.eq("a", "b")).isFalse();
        assertThat(eq.eq("a", "a")).isTrue();
        assertThat(eq.eq("b", "a")).isFalse();
    }
}