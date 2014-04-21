package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.data.functions.Functions;

import java.util.function.Function;

//minimal complete definition: bind OR join
public interface Bind<M> extends Apply<M> {

    // (>>=) (Control.Monad)
    public default <A, B> _<M, B> bind(_<M, A> nestedA, Function<A, _<M, B>> fn) {
        return join(map(fn, nestedA));
    }

    // join (Control.Monad)
    public default <A> _<M, A> join(_<M, _<M, A>> nestedNestedA) {
        return bind(nestedNestedA, Function.<_<M, A>>identity());
    }

    // (>>) (Control.Monad)
    public default <A, B> _<M, B> semicolon(_<M, A> nestedA, _<M, B> nestedB) {
        return bind(nestedA, Functions.<A, _<M, B>>constant(nestedB));
    }

    // (>=>) (Control.Monad) left-to-right Kleisli composition of monads
    public default <A, B, C> Function<A, _<M, C>> kleisli(final Function<A, _<M, B>> f, final Function<B, _<M, C>> g) {
        return a -> bind(f.apply(a), g);
    }
}
