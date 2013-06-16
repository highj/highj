package org.highj.typeclass1.monad;

import org.highj._;

import java.util.function.Function;
import java.util.function.Supplier;

public interface MonadFix<M> extends Monad<M> {
    //mfix :: (a -> m a) -> m a
    public <A> _<M,A> mfix(Function<Supplier<A>, _<M,A>> fn);
}
