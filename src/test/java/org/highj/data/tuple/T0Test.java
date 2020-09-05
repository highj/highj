package org.highj.data.tuple;

import org.highj.data.HList;
import org.highj.data.ord.Ordering;
import org.highj.typeclass0.group.Group;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class T0Test {
    @Test
    public void of() {
        assertThat(T0.of()).isSameAs(T0.unit);
    }

    @Test
    public void testToString() {
        assertThat(T0.unit.toString()).isEqualTo("()");
    }

    @Test
    public void toHlist() {
        assertThat(T0.unit.toHlist()).isEqualTo(HList.HNil.nil);
    }

    @Test
    public void eq() {
        assertThat(T0.eq.eq(T0.unit, T0.unit)).isTrue();
    }

    @Test
    public void ord() {
        assertThat(T0.ord.cmp(T0.unit, T0.unit)).isEqualTo(Ordering.EQ);
    }

    @Test
    public void group() {
        Group<T0> group = T0.group;
        assertThat(group.identity()).isEqualTo(T0.unit);
        assertThat(group.apply(T0.unit, T0.unit)).isEqualTo(T0.unit);
        assertThat(group.inverse(T0.unit)).isEqualTo(T0.unit);
    }
}