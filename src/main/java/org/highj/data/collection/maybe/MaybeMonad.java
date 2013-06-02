package org.highj.data.collection.maybe;

import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

import static org.highj.data.collection.Maybe.Just;
import static org.highj.data.collection.Maybe.narrow;

public class MaybeMonad extends MaybeFunctor implements Monad<Maybe.µ> {

    @Override
    public <A> Maybe<A> pure(A a) {
        return Just(a);
    }

    @Override
    public <A, B> Maybe<B> ap(_<Maybe.µ, Function<A, B>> fn, _<Maybe.µ, A> nestedA) {
        return narrow(narrow(fn).cata(Maybe.<B>Nothing(), f1 -> narrow(nestedA).<_<Maybe.µ, B>>cata(
                Maybe.<B>Nothing(), a -> Just(f1.apply(a)))
        ));
    }

    @Override
    public <A, B> Maybe<B> bind(_<Maybe.µ, A> nestedA, Function<A, _<Maybe.µ, B>> fn) {
        return narrow(nestedA).bind(x -> Maybe.narrow(fn.apply(x)));
    }
}
