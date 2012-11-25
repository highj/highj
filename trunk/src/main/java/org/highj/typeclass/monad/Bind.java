package org.highj.typeclass.monad;

import org.highj._;
import org.highj.function.F1;

public interface Bind<µ> extends Apply<µ> {

    // (>>=) (Control.Monad)
    public <A,B> _<µ, B> bind(_<µ, A> nestedA, F1<A, _<µ, B>> fn);

    // join (Control.Monad)
    public <A> _<µ, A> join(_<µ, _<µ, A>> nestedNestedA);

    // (>>) (Control.Monad)
    public <A, B> _<µ, B> semicolon(_<µ, A> nestedA, _<µ, B> nestedB);

    // (>=>) (Control.Monad) left-to-right Kleisli composition of monads
    public <A, B, C> F1<A, _<µ, C>> kleisli(final F1<A, _<µ, B>> f, final F1<B, _<µ, C>> g);
}
