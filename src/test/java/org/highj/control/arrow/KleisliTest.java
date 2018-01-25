package org.highj.control.arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.Hkt;
import org.highj.data.Maybe;
import org.highj.data.eq.Eq;
import org.highj.typeclass2.arrow.ArrowLaw;
import org.junit.Test;

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
    public void arrowLaw() {
        new ArrowLaw<__<Kleisli.µ, Maybe.µ>>(Kleisli.arrow(Maybe.monad)) {
            @Override
            public <B, C> boolean areEqual(__2<__<Kleisli.µ, Maybe.µ>, B, C> one, __2<__<Kleisli.µ, Maybe.µ>, B, C> two, B b, Eq<C> eq) {
                Kleisli<Maybe.µ, B, C> kleisliOne = Hkt.asKleisli(one);
                Kleisli<Maybe.µ, B, C> kleisliTwo = Hkt.asKleisli(two);
                Maybe<C> maybeOne = Hkt.asMaybe(kleisliOne.apply(b));
                Maybe<C> maybeTwo = Hkt.asMaybe(kleisliTwo.apply(b));
                return Maybe.eq(eq).eq(maybeOne, maybeTwo);
            }
        }.test();
    }

    @Test
    public void profunctor() {
    }
}