package org.highj.typeclass2.bifunctor;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.function.Functions;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.derive4j.hkt.TypeEq.as__2;

public interface Biapply<F> extends Bifunctor<F> {

    <A, B, C, D> __2<F, B, D> biapply(__2<F, Function<A, B>, Function<C, D>> fn, __2<F, A, C> ac);

    default <A, B, C, D> __2<F, C, D> rightShift(__2<F, A, B> a, __2<F, C, D> b) {
        return biapply(bimap(
                Functions.constant(Function.identity()),
                Functions.constant(Function.identity()), a), b);
    }

    default <A, B, C, D> __2<F, A, B> leftShift(__2<F, A, B> a, __2<F, C, D> b) {
        return biapply(bimap(Functions.constant(), Functions.constant(), a), b);
    }

    default <A, B, C, AA, BB, CC> __2<F, C, CC> bilift2(
            Function<A, Function<B, C>> f,
            Function<AA, Function<BB, CC>> g,
            __2<F, A, AA> a, __2<F, B, BB> b) {
        return biapply(bimap(f, g, a), b);
    }

    default <A, B, C, D, AA, BB, CC, DD> __2<F, D, DD> bilift3(
            Function<A, Function<B, Function<C, D>>> f,
            Function<AA, Function<BB, Function<CC, DD>>> g,
            __2<F, A, AA> a, __2<F, B, BB> b, __2<F, C, CC> c) {
        return biapply(biapply(bimap(f, g, a), b), c);
    }

    default <X> Apply<__<F, X>> getApply(Semigroup<X> semigroup) {
        return new CurriedApply<F, X>() {

            @Override
            public Biapply<F> bi() {
                return Biapply.this;
            }

            @Override
            public Semigroup<X> semi() {
                return semigroup;
            }
        };
    }

    interface CurriedApply<F, X> extends Bifunctor.CurriedFunctor<F, X>, Apply<__<F, X>> {

        @Override
        Biapply<F> bi();

        Semigroup<X> semi();

        @Override
        default <A, B> __2<F, X, B> ap(__<__<F, X>, Function<A, B>> fn, __<__<F, X>, A> nestedA) {
            __2<F, X, Function<A, B>> uncurriedFn = as__2(fn);
            Function<X, Function<X, X>> dotFn = x -> y -> semi().apply(x, y);
            __2<F, Function<X, X>, Function<A, B>> biFn = bi().first(dotFn, uncurriedFn);
            return bi().biapply(biFn, as__2(nestedA));
        }
    }

}

