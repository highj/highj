package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;
import org.highj.data.tuple.T3;
import org.highj.typeclass1.functor.FunctorLaw;
import org.highj.util.Gen;
import org.highj.util.Gen1;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicativeLaw<F> extends FunctorLaw<F> {

    private final Applicative<F> applicative;

    public ApplicativeLaw(Applicative<F> applicative, Gen1<F> gen1, Eq1<F> eq1) {
        super(applicative, gen1, eq1);
        this.applicative = applicative;
    }


    /**
     * Applying the identity function within pure shouldn't change a value.
     * ap(pure(x -> x), a) == a
     */
    public void pureIdentity() {
        Eq<__<F, String>> eq = eq1.eq1(Eq.fromEquals());
        Gen<__<F, String>> gen = gen1.gen(Gen.stringGen);
        for (__<F, String> a : gen.get(20)) {
            __<F, String> result = applicative.ap(applicative.pure(Function.identity()), a);
            assertThat(eq.eq(result, a)).isTrue();
        }
    }

    /**
     * The application of function composition can be eliminated.
     * ap(ap(ap(pure(compose(p,q)), f1),f2),v) == ap(f1, ap(f2, v))
     */
    public void apComposition() {
        Eq<__<F, Integer>> eq = eq1.eq1(Eq.fromEquals());
        Gen<Function<Integer, Integer>> fn1Gen = Gen.intGen.map(x -> (y -> y + x));
        Gen<__<F, Function<Integer, Integer>>> genF1 = gen1.gen(fn1Gen);
        Gen<Function<Integer, Integer>> fn2Gen = Gen.intGen.map(x -> (y -> y * x));
        Gen<__<F, Function<Integer, Integer>>> genF2 = gen1.gen(fn2Gen);
        Gen<__<F, Integer>> genInt = gen1.gen(Gen.intGen);
        for (T3<__<F, Function<Integer, Integer>>, __<F, Function<Integer, Integer>>, __<F, Integer>> t3
            : Gen.zip(genF1, genF2, genInt).get(20)) {
            __<F, Function<Integer, Integer>> f1 = t3._1();
            __<F, Function<Integer, Integer>> f2 = t3._2();
            __<F, Integer> v = t3._3();
            __<F, Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>>> pureComp
                = applicative.pure(f -> g -> a -> f.apply(g.apply(a)));
            __<F, Integer> compResult = applicative.ap(applicative.ap(applicative.ap(pureComp, f1), f2), v);
            __<F, Integer> eliminatedResult = applicative.ap(f1, applicative.ap(f2, v));
            assertThat(eq.eq(compResult, eliminatedResult)).isTrue();
        }
    }

    /**
     * Applying a function within pure to an element within pure should be the same as the element
     * applied to the function within pure.
     * ap(pure(f), pure(e)) == pure(f.apply(e))
     */
    public void apHomomorphism() {
        Eq<__<F, Integer>> eq = eq1.eq1(Eq.fromEquals());
        for (String e : Gen.stringGen.get(20)) {
            __<F, Integer> result = applicative.ap(applicative.pure(String::length), applicative.pure(e));
            assertThat(eq.eq(result, applicative.pure(e.length()))).isTrue();
        }
    }

    /**
     * Applying functions to an element within pure should be the same as applying the
     * function application of that element to the functions.
     * ap(fn, pure(e)) == ap(pure(f -> f.apply(e)), fn)
     */
    public void apInterchange() {
        Eq<__<F, Integer>> eq = eq1.eq1(Eq.fromEquals());
        Gen<Function<Integer, Integer>> fnGen = Gen.intGen.map(x -> (y -> y + x));
        Gen<__<F, Function<Integer, Integer>>> gen = gen1.gen(fnGen);
        for (__<F, Function<Integer, Integer>> fns : gen.get(20)) {
            __<F, Integer> fnResult = applicative.ap(fns, applicative.pure(10));
            __<F, Integer> applyResult = applicative.ap(applicative.pure(f -> f.apply(10)), fns);
            assertThat(eq.eq(fnResult, applyResult)).isTrue();
        }
    }

    /**
     * Functor.map can be implemented in terms of Applicative.ap and Applicative.pure.
     * map(f, a) == ap(pure(f), a)
     */
    public void canImplementMap() {
        Eq<__<F, Integer>> eq = eq1.eq1(Eq.fromEquals());
        Gen<__<F, String>> gen = gen1.gen(Gen.stringGen);
        for (__<F, String> a : gen.get(20)) {
            __<F, Integer> mapResult = applicative.map(String::length, a);
            __<F, Integer> apResult = applicative.ap(applicative.pure(String::length), a);
            assertThat(eq.eq(mapResult, apResult)).isTrue();
        }
    }

    @Override
    public void test() {
        pureIdentity();
        apComposition();
        apHomomorphism();
        apInterchange();
        canImplementMap();
        super.test();
    }

}
