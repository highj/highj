package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.typeclass1.functor.Functor;
import org.highj.util.Gen;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctorLaw<F> {

    private final Functor<F> functor;
    private final Gen<__<F, String>> gen;

    private FunctorLaw(Functor<F> functor, Gen<__<F, String>> gen) {
        this.functor = functor;
        this.gen = gen;
    }

    private void mapIdentity() {
        for (__<F, String> a : gen.get(20)) {
            assertThat(functor.map(Function.identity(), a)).isEqualTo(a);
        }
    }

    private void mapComposition() {
        Function<String, Integer> fab = String::length;
        Function<Integer, Long> fbc = i -> Long.valueOf(i) * Long.valueOf(i);
        for (__<F, String> a : gen.get(20)) {
            assertThat(functor.map(fab.andThen(fbc), a)).isEqualTo(
                    functor.map(fbc, functor.map(fab, a)));
        }
    }

    public static <F> void test(Functor<F> functor, Gen<__<F, String>> gen) {
        FunctorLaw<F> law = new FunctorLaw<>(functor, gen);
        law.mapIdentity();
        law.mapComposition();
    }

}
