package org.highj.data.collection.maybe;

import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.typeclass1.monad.MonadFix;
import org.highj.util.Lazy;

import java.util.function.Function;
import java.util.function.Supplier;

public class MaybeMonadFix extends MaybeMonad implements MonadFix<Maybe.µ> {

    //mfix f = let a = f (unJust a) in a
    //   where unJust (Just x) = x
    //         unJust Nothing  = error "mfix Maybe: Nothing"

    @Override
    public <A> _<Maybe.µ, A> mfix(Function<Supplier<A>, _<Maybe.µ, A>> fn) {
        Lazy<A> lazy = new Lazy<>();
        lazy.set(Maybe.narrow(fn.apply(lazy)).get());
        return Maybe.Just(lazy.get());
    }
}
