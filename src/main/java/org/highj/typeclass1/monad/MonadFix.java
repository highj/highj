package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;

import java.util.function.Function;
import java.util.function.Supplier;

public interface MonadFix<M> extends Monad<M> {
    //mfix :: (a -> m a) -> m a
    <A> __<M,A> mfix(Function<Supplier<A>, __<M,A>> fn);
}
