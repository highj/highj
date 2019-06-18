package org.highj.data.instance.maybe;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.typeclass1.monad.MonadPlus;

import static org.highj.Hkt.asMaybe;
import static org.highj.data.Maybe.*;

public interface MaybeMonadPlus extends MaybeMonad, MonadPlus<µ> {

    Bias bias();

    @Override
    default <A> Maybe<A> mzero() {
        return Nothing();
    }

    @Override
    default <A> Maybe<A> mplus(__<µ, A> first, __<µ, A> second) {
        Maybe<A> one = asMaybe(first);
        Maybe<A> two = asMaybe(second);
        return one.isNothing()
                ? two
                : two.isNothing() ? one : bias().select(one, two);
    }
}
