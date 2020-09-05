package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;
import org.highj.util.Gen;
import org.highj.util.Gen1;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class MonadLaw<M> extends ApplicativeLaw<M> {
    private final Monad<M> monad;

    public MonadLaw(Monad<M> monad, Gen1<M> gen1, Eq1<M> eq1) {
        super(monad, gen1, eq1);
        this.monad = monad;
    }

    // pure a >>= f ≡ f a
    public void leftIdentity() {
        Eq<__<M, Integer>> eq = eq1.eq1(Eq.fromEquals());
        Gen<String> gen = Gen.stringGen;
        Function<String, __<M, Integer>> f = (String x) -> monad.pure(x.length());
        for (String a : gen.get(20)) {
            __<M, Integer> lhs = monad.bind(monad.pure(a), f);
            __<M, Integer> rhs = f.apply(a);
            assertThat(eq.eq(lhs, rhs)).isTrue();
        }
    }

    // m >>= pure ≡ m
    public void rightIdentity() {
        Eq<__<M, String>> eq = eq1.eq1(Eq.fromEquals());
        Gen<__<M, String>> gen = gen1.gen(Gen.stringGen);
        for (__<M, String> m : gen.get(20)) {
            __<M, String> lhs = monad.bind(m, monad::<String>pure);
            __<M, String> rhs = m;
            assertThat(eq.eq(lhs, rhs)).isTrue();
        }
    }

    // (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)
    public void associativity() {
        Eq<__<M, String>> eq = eq1.eq1(Eq.fromEquals());
        Gen<__<M, String>> gen = gen1.gen(Gen.stringGen);
        Function<String, __<M, Integer>> f = (String x) -> monad.pure(x.length());
        Function<Integer, __<M, String>> g = (Integer x) -> monad.pure(Integer.toOctalString(x));
        for (__<M, String> m : gen.get(20)) {
            __<M, String> lhs = monad.bind(monad.bind(m, f), g);
            __<M, String> rhs = monad.bind(m, x -> monad.bind(f.apply(x), g));
            assertThat(eq.eq(lhs, rhs)).isTrue();
        }
    }

    @Override
    public void test() {
        leftIdentity();
        rightIdentity();
        associativity();
        super.test();
    }

}
