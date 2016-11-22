package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.highj.data.eq.PartialEq;
import org.highj.typeclass1.functor.Functor;
import org.highj.util.Gen;
import org.highj.util.PartialGen;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctorLaw<F> {

    private final Functor<F> functor;
    protected final PartialGen<F> partialGen;
    protected final PartialEq<F> partialEq;

    public FunctorLaw(Functor<F> functor, PartialGen<F> partialGen, PartialEq<F> partialEq) {
        this.functor = functor;
        this.partialGen = partialGen;
        this.partialEq = partialEq;
    }

    /**
     * Mapping over the identity function should not change the value.
     * map(x -> x, a) == a
     */
    public void mapIdentity() {
        Eq<__<F, String>> eq = partialEq.deriveEq(Eq.fromEquals());
        Gen<__<F, String>> gen = partialGen.deriveGen(Gen.stringGen);
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
        Gen<__<F, String>> gen = partialGen.deriveGen(Gen.stringGen);
        Eq<__<F, Long>> eq = partialEq.deriveEq(Eq.fromEquals());
        for (__<F, String> a : gen.get(20)) {
            __<F, Long> mapComposed = functor.map(fab.andThen(fbc), a);
            __<F, Long> mapSeparated = functor.map(fbc, functor.map(fab, a));
            assertThat(eq.eq(mapComposed, mapSeparated)).isTrue();
        }
    }

    public void testAll() {
        test();
    }

    public void test() {
        mapIdentity();
        mapComposition();
    }

}
