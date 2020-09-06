package org.highj.data;

import org.highj.data.tuple.T2;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MultiSetTest {

    @Test
    public void apply() {
        assertThat(MultiSet.empty().apply("x")).isEqualTo(0);
        assertThat(MultiSet.of1("x", "y", "z").apply("x")).isEqualTo(1);
        assertThat(MultiSet.of1("x", "y", "z").apply("a")).isEqualTo(0);
        assertThat(MultiSet.of1("x", "x", "x").apply("x")).isEqualTo(3);
    }

    @Test
    public void set() {
        assertThat(MultiSet.empty().set("x", 5)).containsExactly(T2.of("x", 5));
        assertThat(MultiSet.of1("x", "x").set("x", 5)).containsExactly(T2.of("x", 5));
        assertThat(MultiSet.of1("x", "x").set("x", 1)).containsExactly(T2.of("x", 1));
        assertThat(MultiSet.of1("x", "x").set("x", 0)).isEmpty();

        assertThatThrownBy(() -> MultiSet.of1("x", "x").set("x", -1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void remove() {
        assertThat(MultiSet.empty().remove("x")).isEmpty();
        assertThat(MultiSet.of1("x", "y", "z").remove("y")).containsExactlyInAnyOrder(
            T2.of("x", 1), T2.of("z", 1));
        assertThat(MultiSet.of1("x", "y", "z").remove("a")).containsExactlyInAnyOrder(
            T2.of("x", 1), T2.of("y", 1), T2.of("z", 1));
    }

    @Test
    public void plus1() {
        assertThat(MultiSet.empty().plus1("x")).containsExactly(T2.of("x", 1));
        assertThat(MultiSet.of1("x", "x").plus1("x")).containsExactly(T2.of("x", 3));
    }

    @Test
    public void plus() {
        assertThat(MultiSet.empty().plus("x", 4)).containsExactly(T2.of("x", 4));
        assertThat(MultiSet.of1("x", "x").plus("x", 4)).containsExactly(T2.of("x", 6));
        assertThat(MultiSet.of1("x", "x").plus("x", 0)).containsExactly(T2.of("x", 2));

        assertThatThrownBy(() -> MultiSet.of1("x", "x").plus("x", -1))
            .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    public void minus1() {
        assertThat(MultiSet.empty().minus1("x")).isEmpty();
        assertThat(MultiSet.of1("x").minus1("x")).isEmpty();
        assertThat(MultiSet.of1("x", "x", "x").minus1("x")).containsExactly(T2.of("x", 2));
    }

    @Test
    public void minus() {
        assertThat(MultiSet.empty().minus("x", 0)).isEmpty();
        assertThat(MultiSet.empty().minus("x", 6)).isEmpty();
        assertThat(MultiSet.of1("x", "x").minus("x", 2)).isEmpty();
        assertThat(MultiSet.of1("x", "x").minus("x", 20)).isEmpty();
        assertThat(MultiSet.of1("x", "x", "x").minus("x", 2)).containsExactly(T2.of("x", 1));
        assertThat(MultiSet.of1("x", "x", "x").minus("x", 0)).containsExactly(T2.of("x", 3));

        assertThatThrownBy(() -> MultiSet.of1("x", "x").minus("x", -1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void isEmpty() {
        assertThat(MultiSet.empty().isEmpty()).isTrue();
        assertThat(MultiSet.of1("x").isEmpty()).isFalse();
        assertThat(MultiSet.of1("x").minus1("x").isEmpty()).isTrue();
    }

    @Test
    public void empty() {
        assertThat(MultiSet.empty()).isEmpty();
    }

    @Test
    public void of1() {
        assertThat(MultiSet.of1()).isEmpty();
        assertThat(MultiSet.of1("x")).containsExactly(T2.of("x", 1));
        assertThat(MultiSet.of1("x", "y", "x")).containsExactlyInAnyOrder(
            T2.of("x", 2), T2.of("y", 1));
    }

    @Test
    public void size() {
        assertThat(MultiSet.empty().size()).isEqualTo(0);
        assertThat(MultiSet.of1("x", "y", "x").size()).isEqualTo(2);
    }

    @Test
    public void totalCount() {
        assertThat(MultiSet.empty().totalCount()).isEqualTo(0);
        assertThat(MultiSet.of1("x", "y", "x").totalCount()).isEqualTo(3);
    }

    @Test
    public void iterator() {
        assertThat(toIterable(MultiSet.empty().iterator())).isEmpty();
        assertThat(toIterable(MultiSet.of1("x", "y", "x").iterator())).containsExactlyInAnyOrder(
            T2.of("x", 2), T2.of("y", 1));
    }

    @Test
    public void testToString() {
        assertThat(MultiSet.empty().toString()).isEqualTo("MultiSet()");
        assertThat(MultiSet.of1(3, 1, 3).toString()).isEqualTo("MultiSet((1,1),(3,2))");
    }

    @Test
    public void toJavaStream() {
        assertThat(MultiSet.empty().toJavaStream()).isEmpty();
        assertThat(MultiSet.of1("x", "y", "x").toJavaStream())
            .containsExactlyInAnyOrder(T2.of("x", 2), T2.of("y", 1));
    }

    @Test
    public void toSet() {
        assertThat((Iterable<?>) (MultiSet.empty().toSet())).isEmpty();
        assertThat((Iterable<Integer>) (MultiSet.of1(1, 1, 2, 3, 5, 8).toSet()))
            .containsExactlyInAnyOrder(1, 2, 3, 5, 8);
    }

    @Test
    public void map() {
        assertThat(MultiSet.<String>empty().map(String::length)).isEmpty();
        assertThat(MultiSet.of1("foo", "bar", "baz", "quux").map(String::length))
            .containsExactlyInAnyOrder(T2.of(3, 3), T2.of(4, 1));
    }

    @Test
    public void toJMap() {
        assertThat(MultiSet.empty().toJMap()).isEmpty();
        assertThat(MultiSet.of1("x", "y", "x").toJMap()).containsExactly(
            new AbstractMap.SimpleEntry<>("x", 2),
            new AbstractMap.SimpleEntry<>("y", 1));
    }

    @Test
    public void testEquals() {
        assertThat(MultiSet.empty().equals(MultiSet.empty())).isTrue();
        assertThat(MultiSet.empty().equals(MultiSet.of1("x"))).isFalse();
        assertThat(MultiSet.empty().equals(MultiSet.of1("x").minus1("x"))).isTrue();
        assertThat(MultiSet.of1("x", "x", "y").equals(MultiSet.of1("x", "y", "x"))).isTrue();
        assertThat(MultiSet.of1("x", "x", "y").equals(MultiSet.of1("x", "y"))).isFalse();
        assertThat(MultiSet.of1("x", "x", "y").equals(MultiSet.of1("x", "y", "z"))).isFalse();
    }

    @Test
    public void testHashCode() {
        assertThat(MultiSet.of1("x", "x", "y").hashCode()).isEqualTo(MultiSet.of1("x", "y", "x").hashCode());
        assertThat(MultiSet.of1("x", "x", "y").hashCode()).isNotEqualTo(MultiSet.of1("x", "y", "z").hashCode());
        assertThat(MultiSet.of1("x", "x", "y").hashCode()).isNotEqualTo(MultiSet.of1("x", "y", "x", "y").hashCode());
    }

    private static <T> Iterable<T> toIterable(Iterator<T> it) {
        return () -> it;
    }
}
