package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.highj.data.Maybe.Just;
import static org.highj.data.Maybe.Nothing;
import static org.highj.data.These.*;

public class TheseTest {

    @Test
    public void these() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.these(a -> a * 7, String::length, (a, b) -> a + b.length())).isEqualTo(42);
        assertThat(that.these(a -> a * 7, String::length, (a, b) -> a + b.length())).isEqualTo(3);
        assertThat(both.these(a -> a * 7, String::length, (a, b) -> a + b.length())).isEqualTo(7);
    }

    @Test
    public void isThis() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.isThis()).isTrue();
        assertThat(that.isThis()).isFalse();
        assertThat(both.isThis()).isFalse();
    }

    @Test
    public void isThat() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.isThat()).isFalse();
        assertThat(that.isThat()).isTrue();
        assertThat(both.isThat()).isFalse();
    }

    @Test
    public void isBoth() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.isBoth()).isFalse();
        assertThat(that.isBoth()).isFalse();
        assertThat(both.isBoth()).isTrue();
    }

    @Test
    public void hasFirst() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.hasFirst()).isTrue();
        assertThat(that.hasFirst()).isFalse();
        assertThat(both.hasFirst()).isTrue();
    }

    @Test
    public void hasSecond() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.hasSecond()).isFalse();
        assertThat(that.hasSecond()).isTrue();
        assertThat(both.hasSecond()).isTrue();
    }

    @Test
    public void _this() {
        These<Integer, String> this_ = This(6);
        assertThat(this_.justThis().get()).isEqualTo(6);
        assertThatThrownBy(() -> This(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void _that() {
        These<Integer, String> that = That("foo");
        assertThat(that.justThat().get()).isEqualTo("foo");
        assertThatThrownBy(() -> That(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void _both() {
        These<Integer, String> both = Both(3, "quux");
        assertThat(both.justBoth().get()).isEqualTo(T2.of(3, "quux"));
        assertThatThrownBy(() -> Both(2, null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> Both(null, 2)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> Both(null, null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void fromThese() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.fromThese(8, "bar")).isEqualTo(T2.of(6, "bar"));
        assertThat(that.fromThese(8, "bar")).isEqualTo(T2.of(8, "foo"));
        assertThat(both.fromThese(8, "bar")).isEqualTo(T2.of(3, "quux"));
    }

    @Test
    public void mergeThese() {
        These<Integer, Integer> this_ = This(6);
        These<Integer, Integer> that = That(7);
        These<Integer, Integer> both = Both(13, 5);
        assertThat(These.mergeThese(this_, (a, b) -> a - b)).isEqualTo(6);
        assertThat(These.mergeThese(that, (a, b) -> a - b)).isEqualTo(7);
        assertThat(These.mergeThese(both, (a, b) -> a - b)).isEqualTo(8);
    }

    @Test
    public void mergeTheseWith() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.mergeTheseWith(a -> 2 * a, String::length, (x, y) -> x * y)).isEqualTo(12);
        assertThat(that.mergeTheseWith(a -> 2 * a, String::length, (x, y) -> x * y)).isEqualTo(3);
        assertThat(both.mergeTheseWith(a -> 2 * a, String::length, (x, y) -> x * y)).isEqualTo(24);
    }

    @Test
    public void justThis() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.justThis()).isEqualTo(Just(6));
        assertThat(that.justThis()).isEqualTo(Nothing());
        assertThat(both.justThis()).isEqualTo(Nothing());
    }

    @Test
    public void justThat() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.justThat()).isEqualTo(Nothing());
        assertThat(that.justThat()).isEqualTo(Just("foo"));
        assertThat(both.justThat()).isEqualTo(Nothing());
    }

    @Test
    public void justBoth() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.justBoth()).isEqualTo(Nothing());
        assertThat(that.justBoth()).isEqualTo(Nothing());
        assertThat(both.justBoth()).isEqualTo(Just(T2.of(3, "quux")));
    }

    @Test
    public void justFirst() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.justFirst()).isEqualTo(Just(6));
        assertThat(that.justFirst()).isEqualTo(Nothing());
        assertThat(both.justFirst()).isEqualTo(Just(3));
    }

    @Test
    public void justSecond() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.justSecond()).isEqualTo(Nothing());
        assertThat(that.justSecond()).isEqualTo(Just("foo"));
        assertThat(both.justSecond()).isEqualTo(Just("quux"));
    }

    @Test
    public void mapThese() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.mapThese(a -> a + 3, String::length)).isEqualTo(This(9));
        assertThat(that.mapThese(a -> a + 3, String::length)).isEqualTo(That(3));
        assertThat(both.mapThese(a -> a + 3, String::length)).isEqualTo(Both(6, 4));
    }

    @Test
    public void mapThis() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.mapThis(a -> a + 3)).isEqualTo(This(9));
        assertThat(that.mapThis(a -> a + 3)).isEqualTo(That("foo"));
        assertThat(both.mapThis(a -> a + 3)).isEqualTo(Both(6, "quux"));
    }

    @Test
    public void mapThat() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.mapThat(String::length)).isEqualTo(This(6));
        assertThat(that.mapThat(String::length)).isEqualTo(That(3));
        assertThat(both.mapThat(String::length)).isEqualTo(Both(3, 4));
    }

    @Test
    public void flip() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.flip()).isEqualTo(That(6));
        assertThat(that.flip()).isEqualTo(This("foo"));
        assertThat(both.flip()).isEqualTo(Both("quux", 3));
    }

    @Test
    public void here() {
        These<Integer, String> this1 = This(6);
        These<Integer, String> this2 = This(7);
        These<Integer, String> that = That("foo");
        These<Integer, String> both1 = Both(4, "quux");
        These<Integer, String> both2 = Both(5, "quux");
        Function<Integer, __<Maybe.µ, Integer>> halfIfEven = a -> a % 2 == 0 ? Just(a / 2) : Nothing();
        assertThat(this1.here(Maybe.monad, halfIfEven)).isEqualTo(Just(This(3)));
        assertThat(this2.here(Maybe.monad, halfIfEven)).isEqualTo(Nothing());
        assertThat(that.here(Maybe.monad, halfIfEven)).isEqualTo(Just(That("foo")));
        assertThat(both1.here(Maybe.monad, halfIfEven)).isEqualTo(Just(Both(2, "quux")));
        assertThat(both2.here(Maybe.monad, halfIfEven)).isEqualTo(Nothing());
    }

    @Test
    public void there() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that1 = That("foo!");
        These<Integer, String> that2 = That("foo");
        These<Integer, String> both1 = Both(3, "quux");
        These<Integer, String> both2 = Both(3, "quux!");
        Function<String, __<Maybe.µ, Integer>> halfIfEven =
            a -> a.length() % 2 == 0 ? Just(a.length() / 2) : Nothing();
        assertThat(this_.there(Maybe.monad, halfIfEven)).isEqualTo(Just(This(6)));
        assertThat(that1.there(Maybe.monad, halfIfEven)).isEqualTo(Just(That(2)));
        assertThat(that2.there(Maybe.monad, halfIfEven)).isEqualTo(Nothing());
        assertThat(both1.there(Maybe.monad, halfIfEven)).isEqualTo(Just(Both(3, 2)));
        assertThat(both2.there(Maybe.monad, halfIfEven)).isEqualTo(Nothing());
    }

    @Test
    public void catThis() {
        List<These<Integer, String>> list = List.of(This(3), That("foo"), That("bar"), Both(3, "quux"), This(12), Both(2, "baz"));
        assertThat(These.catThis(list)).containsExactly(3, 12);
    }

    @Test
    public void catThat() {
        List<These<Integer, String>> list = List.of(This(3), That("foo"), That("bar"), Both(3, "quux"), This(12), Both(2, "baz"));
        assertThat(These.catThat(list)).containsExactly("foo", "bar");
    }

    @Test
    public void catBoth() {
        List<These<Integer, String>> list = List.of(This(3), That("foo"), That("bar"), Both(3, "quux"), This(12), Both(2, "baz"));
        assertThat(These.catBoth(list)).containsExactly(T2.of(3, "quux"), T2.of(2, "baz"));
    }

    @Test
    public void catFirst() {
        List<These<Integer, String>> list = List.of(This(3), That("foo"), That("bar"), Both(3, "quux"), This(12), Both(2, "baz"));
        assertThat(These.catFirst(list)).containsExactly(3, 3, 12, 2);
    }

    @Test
    public void catSecond() {
        List<These<Integer, String>> list = List.of(This(3), That("foo"), That("bar"), Both(3, "quux"), This(12), Both(2, "baz"));
        assertThat(These.catSecond(list)).containsExactly("foo", "bar", "quux", "baz");
    }

    @Test
    public void _toString() {
        These<Integer, String> this_ = This(6);
        These<Integer, String> that = That("foo");
        These<Integer, String> both = Both(3, "quux");
        assertThat(this_.toString()).isEqualTo("This(6)");
        assertThat(that.toString()).isEqualTo("That(foo)");
        assertThat(both.toString()).isEqualTo("Both(3,quux)");
    }

    @Test
    public void _equals() {
        assertThat(This(6).equals(This(6))).isTrue();
        assertThat(This(6).equals(null)).isFalse();
        assertThat(This(6).equals(This(9))).isFalse();
        assertThat(This(6).equals(That(6))).isFalse();
        assertThat(This(6).equals(Both(6, 6))).isFalse();

        assertThat(That(6).equals(That(6))).isTrue();
        assertThat(That(6).equals(null)).isFalse();
        assertThat(That(6).equals(That(9))).isFalse();
        assertThat(That(6).equals(This(6))).isFalse();
        assertThat(That(6).equals(Both(6, 6))).isFalse();

        assertThat(Both(6, 7).equals(Both(6, 7))).isTrue();
        assertThat(Both(6, 7).equals(null)).isFalse();
        assertThat(Both(6, 7).equals(Both(6, 8))).isFalse();
        assertThat(Both(6, 7).equals(Both(5, 7))).isFalse();
        assertThat(Both(6, 7).equals(Both(5, 8))).isFalse();
        assertThat(Both(6, 7).equals(This(6))).isFalse();
        assertThat(Both(6, 7).equals(That(7))).isFalse();
    }
}