package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.function.Functions;

import java.util.function.Function;

//minimal complete definition: bind OR join
public interface Bind<µ> extends Apply<µ> {

    // (>>=) (Control.Monad)
    public default <A, B> _<µ, B> bind(_<µ, A> nestedA, Function<A, _<µ, B>> fn) {
        return join(map(fn, nestedA));
    }

    // join (Control.Monad)
    public default <A> _<µ, A> join(_<µ, _<µ, A>> nestedNestedA) {
        return bind(nestedNestedA, Functions.<_<µ, A>>id());
    }

    // (>>) (Control.Monad)
    public default <A, B> _<µ, B> semicolon(_<µ, A> nestedA, _<µ, B> nestedB) {
        return bind(nestedA, Functions.<A, _<µ, B>>constant(nestedB));
    }

    // (>=>) (Control.Monad) left-to-right Kleisli composition of monads
    public default <A, B, C> Function<A, _<µ, C>> kleisli(final Function<A, _<µ, B>> f, final Function<B, _<µ, C>> g) {
        return a -> bind(f.apply(a), g);
    }
}
