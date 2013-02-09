package org.highj.typeclass2.bifunctor;

import org.highj.__;
import org.highj.function.Functions;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface BiApply<F> extends BiFunctor<F> {

    // (<<.>>)
    public <A, B, C, D> __<F, B, D> biApply(__<F, Function<A, B>, Function<C, D>> fn, __<F, A, C> ac);

    // (.>>)
    public default <A, B, C, D> __<F, C, D> rightShift(__<F, A, B> a, __<F, C, D> b) {
        // a .>> b = bimap (const id) (const id) <<$>> a <<.>> b
        return biApply(biMap(
                Functions.<A, Function<C, C>>constant(Functions.<C>id()),
                Functions.<B, Function<D, D>>constant(Functions.<D>id()), a), b);
    }

    // (<<.)
    public default <A, B, C, D> __<F, A, B> leftShift(__<F, A, B> a, __<F, C, D> b) {
        // a <<. b = bimap const const <<$>> a <<.>> b
        return biApply(biMap(Functions.<A, C>constant(), Functions.<B, D>constant(), a), b);
    }

    public default <A, B, C, AA, BB, CC> __<F, C, CC> bilift2(
            Function<A, Function<B, C>> f,
            Function<AA, Function<BB, CC>> g,
            __<F, A, AA> a, __<F, B, BB> b) {
        // bilift2 f g a b = bimap f g <<$>> a <<.>> b
        return biApply(biMap(f, g, a), b);
    }

    public default <A, B, C, D, AA, BB, CC, DD> __<F, D, DD> bilift3(
            Function<A, Function<B, Function<C, D>>> f,
            Function<AA, Function<BB, Function<CC, DD>>> g,
            __<F, A, AA> a, __<F, B, BB> b, __<F, C, CC> c) {
        // bilift3 f g a b c = bimap f g <<$>> a <<.>> b <<.>> c
        return biApply(biApply(biMap(f, g, a), b), c);
    }

    public default <X> Apply<__.µ<F,X>> getApply(Semigroup<X> semigroup) {
        return new CurriedApply<>(this, semigroup);
    }

}

