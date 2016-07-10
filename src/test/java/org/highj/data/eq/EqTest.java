package org.highj.data.eq;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;
import org.junit.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

public class EqTest {

    @Test
    public void narrow() {
        Eq<String> eq = Eq.fromEquals();
        __<Eq.µ, String> hkt = eq;
        assertThat(Eq.narrow(hkt)).isSameAs(eq);
    }

    @Test
    public void fromEquals() {
        Eq<String> eq = Eq.fromEquals();
        assertThat(eq.eq("a", "a")).isTrue();
        assertThat(eq.eq("a", "b")).isFalse();
    }

    @Test
    public void fromObjectIdentity() {
        Eq<String> eq = Eq.fromObjectIdentity();
        String a1 = new String("a");
        String a2 = new String("a");
        assertThat(eq.eq(a1, a1)).isTrue();
        assertThat(eq.eq(a1, a2)).isFalse();
    }

    @Test
    public void contravariant() {
        Eq<String> lengthy = Eq.contravariant.contramap(String::length, Eq.fromEquals());
        assertThat(lengthy.eq("one", "two")).isTrue();
        assertThat(lengthy.eq("one", "three")).isFalse();
    }

    @Test
    public void divisible() {
        Eq<Point> conq = Eq.divisible.conquer();
        assertThat(conq.eq(new Point(2, 3), new Point(1, 7))).isTrue();

        Eq<Point> div = Eq.divisible.divide(p -> T2.of(p.x, p.y), Eq.fromEquals(), Eq.fromEquals());
        assertThat(div.eq(new Point(2, 3), new Point(1, 7))).isFalse();
        assertThat(div.eq(new Point(2, 3), new Point(2, 3))).isTrue();
    }

}