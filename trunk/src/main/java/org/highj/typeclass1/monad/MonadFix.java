package org.highj.typeclass1.monad;

import org.highj._;

import java.util.function.Function;
import java.util.function.Supplier;

public interface MonadFix<µ> extends Monad<µ> {
    //mfix :: (a -> m a) -> m a
    public <A> _<µ,A> mfix(Function<Supplier<A>, _<µ,A>> fn);
}
