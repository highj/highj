package org.highj.typeclass1.functor;

import com.pholser.junit.quickcheck.Property;
import org.derive4j.hkt.__;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public interface FunctorContract<F> {

    Functor<F> subject();

    /**
     * Mapping over the identity function should not change the value.
     * map(x -> x, a) == a
     */
    @Property
    default void mapIdentity(__<F, String> a) {
        Functor<F> functor = subject();
        __<F, String> mappedA = functor.map(Function.identity(), a);
        assertThat(mappedA).isEqualTo(a);
    }

    /**
     * Mapping over a composed function should be the same as mapping successively over both
     * underlying functions.
     * map(p.andThen(q), x) = map(q, map(p, x))
     */
    @Property
    default void mapComposition(__<F, String> a) {
        Functor<F> functor = subject();
        Function<String, Integer> fab = String::length;
        Function<Integer, Long> fbc = i -> Long.valueOf(i) * Long.valueOf(i);
        __<F, Long> mapComposed = functor.map(fab.andThen(fbc), a);
        __<F, Long> mapSeparated = functor.map(fbc, functor.map(fab, a));
        assertThat(mapComposed).isEqualTo(mapSeparated);
    }
}
