package org.highj.typeclass0.group;

import org.highj.data.List;
import org.highj.function.Strings;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MonoidTest {
    @Test
    public void fold() {
        Monoid<String> monoid = Strings.monoid;

        assertThat(monoid.fold(List.empty())).isEqualTo("");

        String result = monoid.fold(List.of("foo", "bar", "baz"));
        assertThat(result).isEqualTo("foobarbaz");
    }

    @Test
    public void times() {
        Monoid<String> monoid = Strings.monoid;

        assertThat(monoid.times("foo", 0)).isEqualTo("");
        assertThat(monoid.times("foo", 1)).isEqualTo("foo");
        assertThat(monoid.times("foo", 5)).isEqualTo("foofoofoofoofoo");
        assertThat(monoid.times("foo", 6)).isEqualTo("foofoofoofoofoofoo");
    }

    @Test
    public void dual() {
        Monoid<String> monoid = Monoid.dual(Strings.monoid);

        assertThat(monoid.identity()).isEqualTo("");
        assertThat(monoid.apply("foo", "bar")).isEqualTo("barfoo");
    }

    @Test
    public void create() {
        Monoid<String> monoid = Monoid.create("", Strings::concat);

        assertThat(monoid.identity()).isEqualTo("");
        assertThat(monoid.apply("foo", "bar")).isEqualTo("foobar");
    }

}