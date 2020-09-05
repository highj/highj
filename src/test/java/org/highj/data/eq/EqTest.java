package org.highj.data.eq;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.tuple.T2;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.highj.Hkt.asEq;

public class EqTest {

    @Test
    public void contravariant() {
        Eq<String> lengthy = Eq.contravariant.contramap(String::length, Eq.fromEquals());
        assertThat(lengthy.eq("one", "two")).isTrue();
        assertThat(lengthy.eq("one", "three")).isFalse();
    }

    @Test
    public void decidable() {
        Eq<Integer> evenFirst = Eq.decidable.choose(
            i -> i % 2 == 0 ? Either.Left(i) : Either.Right(i),
            Eq.divisible.conquer(),
            Eq.divisible.conquer());
        //all even numbers are "equal"
        assertThat(evenFirst.eq(2, 6)).isTrue();
        assertThat(evenFirst.eq(4, 4)).isTrue();
        //all odd numbers are "equal"
        assertThat(evenFirst.eq(1, 5)).isTrue();
        assertThat(evenFirst.eq(3, 3)).isTrue();
        //even numbers are not "equal" to odd numbers
        assertThat(evenFirst.eq(6, 1)).isFalse();
        assertThat(evenFirst.eq(1, 6)).isFalse();

        assertThatThrownBy(() -> Eq.decidable.lose(p -> null))
            .isInstanceOf(AssertionError.class);
    }

    @Test
    public void divisible() {
        Eq<Point> conq = Eq.divisible.conquer();
        assertThat(conq.eq(new Point(2, 3), new Point(1, 7))).isTrue();

        Eq<Point> div = Eq.divisible.divide(p -> T2.of(p.x, p.y), Eq.fromEquals(), Eq.fromEquals());
        assertThat(div.eq(new Point(2, 3), new Point(1, 7))).isFalse();
        assertThat(div.eq(new Point(2, 3), new Point(2, 3))).isTrue();
    }

    @Test
    public void fromEquals() {
        Eq<String> eq = Eq.fromEquals();
        assertThat(eq.eq("a", "a")).isTrue();
        assertThat(eq.eq("a", "b")).isFalse();
    }

    @Test
    public void fromObjectIdentity() {
        Eq<StringBuilder> eq = Eq.fromObjectIdentity();
        StringBuilder a1 = new StringBuilder("a");
        StringBuilder a2 = new StringBuilder("a");
        assertThat(eq.eq(a1, a1)).isTrue();
        assertThat(eq.eq(a1, a2)).isFalse();
    }

    @Test
    public void narrow() {
        Eq<String> eq = Eq.fromEquals();
        __<Eq.µ, String> hkt = eq;
        assertThat(asEq(hkt)).isSameAs(eq);
    }
}