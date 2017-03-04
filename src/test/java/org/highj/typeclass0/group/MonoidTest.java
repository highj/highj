package org.highj.typeclass0.group;

import org.highj.data.List;
import org.highj.function.Strings;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MonoidTest {
    @Test
    public void fold() throws Exception {
        Monoid<String> monoid = Strings.group;

        assertThat(monoid.fold(List.empty())).isEqualTo("");

        String result = monoid.fold(List.of("foo","bar","baz"));
        assertThat(result).isEqualTo("foobarbaz");
    }

    @Test
    public void times() throws Exception {
        Monoid<String> monoid = Strings.group;

        assertThat(monoid.times("foo",0)).isEqualTo("");
        assertThat(monoid.times("foo",1)).isEqualTo("foo");
        assertThat(monoid.times("foo",5)).isEqualTo("foofoofoofoofoo");
        assertThat(monoid.times("foo",6)).isEqualTo("foofoofoofoofoofoo");
    }

    @Test
    public void dual() throws Exception {
        Monoid<String> monoid = Monoid.dual(Strings.group);

        assertThat(monoid.identity()).isEqualTo("");
        assertThat(monoid.apply("foo", "bar")).isEqualTo("barfoo");
    }

    @Test
    public void create() throws Exception {
        Monoid<String> monoid = Monoid.create("", Strings::concat);

        assertThat(monoid.identity()).isEqualTo("");
        assertThat(monoid.apply("foo", "bar")).isEqualTo("foobar");
    }

}