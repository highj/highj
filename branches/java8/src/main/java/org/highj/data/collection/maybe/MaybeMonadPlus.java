package org.highj.data.collection.maybe;

import static org.highj.data.collection.Maybe.*;


import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.typeclass1.monad.MonadPlus;

import java.util.function.Function;

public class MaybeMonadPlus extends MaybeFunctor implements MonadPlus<µ> {
    @Override
    public <A> _<µ, A> pure(A a) {
        return Just(a);
    }

    @Override
    public <A, B> _<µ, B> ap(_<µ, Function<A, B>> fn, _<µ, A> nestedA) {
        return narrow(fn).cata(Maybe.<B>Nothing(), f1 -> narrow(nestedA).<_<µ, B>>cata(
                Maybe.<B>Nothing(), a -> Just(f1.apply(a)))
        );
    }

    @Override
    public <A, B> _<Maybe.µ, B> bind(_<Maybe.µ, A> nestedA, Function<A, _<Maybe.µ, B>> fn) {
        return narrow(nestedA).<B>bind(x -> Maybe.narrow(fn.apply(x)));
    }

    @Override
    public <A> _<Maybe.µ, A> mzero() {
        return Nothing();
    }

    @Override
    public <A> _<Maybe.µ, A> mplus(_<Maybe.µ, A> first, _<Maybe.µ, A> second) {
        return narrow(first).isNothing() ? second : first;
    }
}
