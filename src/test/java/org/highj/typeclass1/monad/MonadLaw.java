package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.highj.data.eq.PartialEq;
import org.highj.util.Gen;
import org.highj.util.PartialGen;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class MonadLaw<M> {
    private final Monad<M> monad;
    private final PartialGen<M> partialGen;
    private final PartialEq<M> partialEq;

    private MonadLaw(Monad<M> monad, PartialGen<M> partialGen, PartialEq<M> partialEq) {
        this.monad = monad;
        this.partialGen = partialGen;
        this.partialEq = partialEq;
    }

    // pure a >>= f ≡ f a
    private void leftIdentity() {
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
    private void rightIdentity() {
        Eq<__<M, String>> eq = partialEq.deriveEq(Eq.fromEquals());
        Gen<__<M, String>> gen = partialGen.deriveGen(Gen.stringGen);
        for (__<M, String> m : gen.get(20)) {
            __<M,String> lhs = monad.bind(m, monad::pure);
            __<M,String> rhs = m;
            assertThat(eq.eq(lhs, rhs)).isTrue();
        }
    }

    // (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)
    private void associativity() {
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

    public static <M> void test(Monad<M> monad, PartialGen<M> partialGen, PartialEq<M> partialEq) {
        MonadLaw<M> law = new MonadLaw<>(monad, partialGen, partialEq);
        law.leftIdentity();
        law.rightIdentity();
        law.associativity();
    }
}
