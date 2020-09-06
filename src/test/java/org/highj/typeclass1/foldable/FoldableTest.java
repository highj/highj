package org.highj.typeclass1.foldable;

import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.data.num.Integers;
import org.highj.function.Strings;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class FoldableTest {

    private final Function<String, Function<String, String>> wrapFn = a -> b -> "(" + a + "," + b + ")";
    private final BiFunction<String, String, String> wrapBiFn = (a, b) -> "(" + a + "," + b + ")";

    @Test
    public void fold() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        assertThat(List.traversable.fold(Integers.multiplicativeMonoid, numbers)).isEqualTo(120);

        assertThat(List.traversable.fold(Integers.multiplicativeMonoid, List.of())).isEqualTo(1);
    }

    @Test
    public void foldMap() {
        List<String> strings = List.of("a", "bb", "ccc", "dddd", "eeeee");
        int result = List.traversable.foldMap(Integers.multiplicativeMonoid, String::length, strings);
        assertThat(result).isEqualTo(120);

        assertThat(List.traversable.foldMap(Integers.multiplicativeMonoid, String::length, List.of()))
            .isEqualTo(1);
    }

    @Test
    public void foldr() {
        List<String> strings = List.of("a", "e", "i", "o");
        String result = List.traversable.foldr(wrapFn, "u", strings);
        assertThat(result).isEqualTo("(a,(e,(i,(o,u))))");

        result = List.traversable.foldr(wrapBiFn, "u", strings);
        assertThat(result).isEqualTo("(a,(e,(i,(o,u))))");
    }

    @Test
    public void foldl() {
        List<String> strings = List.of("e", "i", "o", "u");
        String result = List.traversable.foldl(wrapFn, "a", strings);
        assertThat(result).isEqualTo("((((a,e),i),o),u)");

        result = List.traversable.foldl(wrapBiFn, "a", strings);
        assertThat(result).isEqualTo("((((a,e),i),o),u)");
    }

    @Test
    public void foldr1() {
        List<String> strings = List.of("a", "e", "i", "o", "u");
        Maybe<String> result = List.traversable.foldr1(wrapFn, strings);
        assertThat(result.get()).isEqualTo("(a,(e,(i,(o,u))))");
        assertThat(List.traversable.foldr1(wrapFn, List.of())).isEmpty();

        result = List.traversable.foldr1(wrapBiFn, strings);
        assertThat(result.get()).isEqualTo("(a,(e,(i,(o,u))))");
        assertThat(List.traversable.foldr1(wrapBiFn, List.of())).isEmpty();
    }

    @Test
    public void foldl1() {
        List<String> strings = List.of("a", "e", "i", "o", "u");
        Maybe<String> result = List.traversable.foldl1(wrapFn, strings);
        assertThat(result.get()).isEqualTo("((((a,e),i),o),u)");
        assertThat(List.traversable.foldl1(wrapFn, List.of())).isEmpty();

        result = List.traversable.foldl1(wrapBiFn, strings);
        assertThat(result.get()).isEqualTo("((((a,e),i),o),u)");
        assertThat(List.traversable.foldl1(wrapBiFn, List.of())).isEmpty();
    }

    @Test
    public void fold1() {
        List<String> strings = List.of("a", "e", "i", "o", "u");
        assertThat(List.traversable.fold1(Strings.monoid, strings)).isEqualTo("aeiou");

        assertThatThrownBy(() -> List.traversable.fold1(Strings.monoid, List.of()))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void toList() {
        List<String> strings = List.of("a", "e", "i", "o", "u");
        List<String> result = List.traversable.toList(strings);
        assertThat(result).containsExactly("a", "e", "i", "o", "u");
    }

    @Test
    public void foldMap1() {
        List<String> strings = List.of("a", "bb", "ccc", "dddd", "eeeee");
        Integer result = List.traversable.foldMap1(Integers.multiplicativeMonoid, String::length, strings);
        assertThat(result).isEqualTo(120);

        assertThatThrownBy(() -> List.traversable.foldMap1(Integers.multiplicativeMonoid, String::length, List.of()))
            .isInstanceOf(RuntimeException.class);
    }
}
