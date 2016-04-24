package org.highj.data.collection.maybe;

import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.typeclass1.monad.MonadPlus;

import static org.highj.data.collection.Maybe.*;

public interface MaybeMonadPlus extends MaybeMonad, MonadPlus<µ> {

    Bias bias();

    @Override
    default <A> Maybe<A> mzero() {
        return newNothing();
    }

    @Override
    default <A> Maybe<A> mplus(_<Maybe.µ, A> first, _<Maybe.µ, A> second) {
        Maybe<A> one = narrow(first);
        Maybe<A> two = narrow(second);
        return one.isNothing()
                ? two
                : two.isNothing() ? one : bias().select(one, two);
    }
}
