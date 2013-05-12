package org.highj.data.collection.maybe;

import static org.highj.data.collection.Maybe.*;


import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.typeclass1.monad.MonadPlus;

import java.util.function.Function;

public class MaybeMonadPlus extends MaybeFunctor implements MonadPlus<µ> {
    @Override
    public <A> Maybe<A> pure(A a) {
        return Just(a);
    }

    @Override
    public <A, B> Maybe<B> ap(_<µ, Function<A, B>> fn, _<µ, A> nestedA) {
        return narrow(narrow(fn).cata(Maybe.<B>Nothing(), f1 -> narrow(nestedA).<_<µ, B>>cata(
                Maybe.<B>Nothing(), a -> Just(f1.apply(a)))
        ));
    }

    @Override
    public <A, B> Maybe<B> bind(_<Maybe.µ, A> nestedA, Function<A, _<Maybe.µ, B>> fn) {
        return narrow(nestedA).bind(x -> Maybe.narrow(fn.apply(x)));
    }

    @Override
    public <A> Maybe<A> mzero() {
        return Nothing();
    }

    @Override
    public <A> Maybe<A> mplus(_<Maybe.µ, A> first, _<Maybe.µ, A> second) {
        Maybe<A> one = narrow(first);
        Maybe<A> two = narrow(second);
        return one.isNothing() ? two : one;
    }
}
