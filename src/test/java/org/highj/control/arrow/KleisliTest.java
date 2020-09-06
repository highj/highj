package org.highj.control.arrow;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.data.Maybe.*;

public class KleisliTest {

    @Test
    public void apply() {
        Function<String, __<Maybe.µ, Integer>> fn = s -> JustWhenTrue(s.contains("x"), () -> s.indexOf('x'));
        assertThat(new Kleisli<>(fn).apply("Max")).isEqualTo(Just(2));
        assertThat(new Kleisli<>(fn).apply("Moritz")).isEqualTo(Nothing());
    }

    @Test
    public void lmap() {
        Function<String, __<Maybe.µ, Integer>> fn = s -> JustWhenTrue(s.contains("x"), () -> s.indexOf('x'));
        Kleisli<Maybe.µ, String, Integer> kleisli = new Kleisli<>(fn).lmap(s -> new StringBuilder(s).reverse().toString());
        assertThat(kleisli.apply("Max")).isEqualTo(Just(0));
        assertThat(kleisli.apply("Moritz")).isEqualTo(Nothing());
    }

    @Test
    public void profunctor() {
    }
}