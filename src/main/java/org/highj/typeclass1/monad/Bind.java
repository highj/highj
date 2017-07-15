package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;
import org.highj.control.arrow.Kleisli;
import org.highj.function.Functions;

import java.util.function.Function;

//minimal complete definition: bind OR join
public interface Bind<M> extends Apply<M> {

    // (>>=) (Control.Monad)
    default <A, B> __<M, B> bind(__<M, A> nestedA, Function<A, __<M, B>> fn) {
        return join(map(fn, nestedA));
    }

    // join (Control.Monad)
    default <A> __<M, A> join(__<M, __<M, A>> nestedNestedA) {
        return bind(nestedNestedA, Function.<__<M, A>>identity());
    }

    // (>>) (Control.Monad)
    default <A, B> __<M, B> semicolon(__<M, A> nestedA, __<M, B> nestedB) {
        return bind(nestedA, Functions.<A, __<M, B>>constant(nestedB));
    }

    // (>=>) (Control.Monad) left-to-right Kleisli composition of monads
    default <A, B, C> Function<A, __<M, C>> kleisli(final Function<A, __<M, B>> f, final Function<B, __<M, C>> g) {
        return a -> bind(f.apply(a), g);
    }

    default <A, B, C> Kleisli<M, A, C> kleisli(final Kleisli<M, A, B> f, final Kleisli<M, B, C> g) {
        return new Kleisli<>(a -> bind(f.apply(a), g));
    }
}
