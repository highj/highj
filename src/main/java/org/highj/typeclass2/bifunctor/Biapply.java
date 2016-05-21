package org.highj.typeclass2.bifunctor;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.function.Functions;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface Biapply<F> extends Bifunctor<F> {

    // (<<.>>)
    public <A, B, C, D> __2<F, B, D> biapply(__2<F, Function<A, B>, Function<C, D>> fn, __2<F, A, C> ac);

    // (.>>)
    public default <A, B, C, D> __2<F, C, D> rightShift(__2<F, A, B> a, __2<F, C, D> b) {
        // a .>> b = bimap (const id) (const id) <<$>> a <<.>> b
        return biapply(bimap(
                Functions.<A, Function<C, C>>constant(Function.<C>identity()),
                Functions.<B, Function<D, D>>constant(Function.<D>identity()), a), b);
    }

    // (<<.)
    public default <A, B, C, D> __2<F, A, B> leftShift(__2<F, A, B> a, __2<F, C, D> b) {
        // a <<. b = bimap const const <<$>> a <<.>> b
        return biapply(bimap(Functions.<A, C>constant(), Functions.<B, D>constant(), a), b);
    }

    public default <A, B, C, AA, BB, CC> __2<F, C, CC> bilift2(
            Function<A, Function<B, C>> f,
            Function<AA, Function<BB, CC>> g,
            __2<F, A, AA> a, __2<F, B, BB> b) {
        // bilift2 f g a b = bimap f g <<$>> a <<.>> b
        return biapply(bimap(f, g, a), b);
    }

    public default <A, B, C, D, AA, BB, CC, DD> __2<F, D, DD> bilift3(
            Function<A, Function<B, Function<C, D>>> f,
            Function<AA, Function<BB, Function<CC, DD>>> g,
            __2<F, A, AA> a, __2<F, B, BB> b, __2<F, C, CC> c) {
        // bilift3 f g a b c = bimap f g <<$>> a <<.>> b <<.>> c
        return biapply(biapply(bimap(f, g, a), b), c);
    }

    public default <X> Apply<__<F, X>> getApply(Semigroup<X> semigroup) {
        return new CurriedApply<>(this, semigroup);
    }

}

