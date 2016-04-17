package org.highj.data.collection.maybe;

import static org.highj.data.collection.Maybe.*;


import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.typeclass1.monad.MonadPlus;

public class MaybeMonadPlus extends MaybeMonad implements MonadPlus<µ> {

    public enum Bias {FIRST_JUST, LAST_JUST}

    private final Bias bias;

    public MaybeMonadPlus(Bias bias) {
        this.bias = bias;
    }

    @Override
    public <A> Maybe<A> mzero() {
        return newNothing();
    }

    @Override
    public <A> Maybe<A> mplus(_<Maybe.µ, A> first, _<Maybe.µ, A> second) {
        Maybe<A> one = narrow(first);
        Maybe<A> two = narrow(second);
        return one.isNothing() ? two :
               two.isNothing() ? one :
               bias == Bias.FIRST_JUST ? one : two;
    }
}
