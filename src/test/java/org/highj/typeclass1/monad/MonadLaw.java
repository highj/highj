package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.highj.data.eq.PartialEq;
import org.highj.util.Gen;
import org.highj.util.PartialGen;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class MonadLaw<M> extends ApplicativeLaw<M> {
    private final Monad<M> monad;

    public MonadLaw(Monad<M> monad, PartialGen<M> partialGen, PartialEq<M> partialEq) {
        super(monad, partialGen, partialEq);
        this.monad = monad;
    }

    // pure a >>= f ≡ f a
    public void leftIdentity() {
        Eq<__<M, Integer>> eq = partialEq.deriveEq(Eq.fromEquals());
        Gen<String> gen = Gen.stringGen;
        Function<String, __<M,Integer>> f = (String x) -> monad.pure(x.length());
        for (String a : gen.get(20)) {
            __<M,Integer> lhs = monad.bind(monad.pure(a), f);
            __<M,Integer> rhs = f.apply(a);
            assertThat(eq.eq(lhs, rhs)).isTrue();
        }
    }

    // m >>= pure ≡ m
    public void rightIdentity() {
        Eq<__<M, String>> eq = partialEq.deriveEq(Eq.fromEquals());
        Gen<__<M, String>> gen = partialGen.deriveGen(Gen.stringGen);
        for (__<M, String> m : gen.get(20)) {
            __<M,String> lhs = monad.bind(m, monad::pure);
            __<M,String> rhs = m;
            assertThat(eq.eq(lhs, rhs)).isTrue();
        }
    }

    // (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)
    public void associativity() {
        Eq<__<M, String>> eq = partialEq.deriveEq(Eq.fromEquals());
        Gen<__<M, String>> gen = partialGen.deriveGen(Gen.stringGen);
        Function<String,__<M,Integer>> f = (String x) -> monad.pure(x.length());
        Function<Integer,__<M,String>> g = (Integer x) -> monad.pure(Integer.toOctalString(x));
        for (__<M, String> m : gen.get(20)) {
            __<M,String> lhs = monad.bind(monad.bind(m, f), g);
            __<M,String> rhs = monad.bind(m, x -> monad.bind(f.apply(x), g));
            assertThat(eq.eq(lhs, rhs)).isTrue();
        }
    }

    @Override
    public void testAll() {
        super.testAll();
        test();
    }

    @Override
    public void test() {
        leftIdentity();
        rightIdentity();
        associativity();
    }

}
