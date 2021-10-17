package org.highj.typeclass1.functor;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;
import org.highj.util.Gen;
import org.highj.util.Gen1;
import org.highj.util.Law;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctorLaw<F> implements Law {

    private final Functor<F> functor;
    protected final Gen1<F> gen1;
    protected final Eq1<F> eq1;

    public FunctorLaw(Functor<F> functor, Gen1<F> gen1, Eq1<F> eq1) {
        this.functor = functor;
        this.gen1 = gen1;
        this.eq1 = eq1;
    }

    /**
     * Mapping over the identity function should not change the value.
     * map(x -> x, a) == a
     */
    public void mapIdentity() {
        Eq<__<F, String>> eq = eq1.eq1(Eq.fromEquals());
        Gen<__<F, String>> gen = gen1.gen(Gen.stringGen);
        for (__<F, String> a : gen.get(20)) {
            __<F, String> mappedA = functor.map(Function.identity(), a);
            assertThat(eq.eq(mappedA, a)).isTrue();
        }
    }

    /**
     * Mapping over a composed function should be the same as mapping successively over both
     * underlying functions.
     * map(p.andThen(q), x) = map(q, map(p, x))
     */
    public void mapComposition() {
        Function<String, Integer> fab = String::length;
        Function<Integer, Long> fbc = i -> Long.valueOf(i) * Long.valueOf(i);
        Gen<__<F, String>> gen = gen1.gen(Gen.stringGen);
        Eq<__<F, Long>> eq = eq1.eq1(Eq.fromEquals());
        for (__<F, String> a : gen.get(20)) {
            __<F, Long> mapComposed = functor.map(fab.andThen(fbc), a);
            __<F, Long> mapSeparated = functor.map(fbc, functor.map(fab, a));
            assertThat(eq.eq(mapComposed, mapSeparated)).isTrue();
        }
    }

    @Override
    public void test() {
        mapIdentity();
        mapComposition();
    }

}
