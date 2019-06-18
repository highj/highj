package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;

import java.util.function.BiFunction;

/**
 * Monadic zipping.
 *
 * Note that the unzip method was pulled up to functor.
 *
 * @param <M> the monadic type
 */
public interface MonadZip<M> extends Monad<M> {

    //mzipWith :: (a -> b -> c) -> m a -> m b -> m c
    <A, B, C> __<M, C> mzipWith(BiFunction<A, B, C> fn, __<M, A> ma, __<M, B> mb);

    //mzip :: m a -> m b -> m (a, b)
    default <A, B> __<M, T2<A, B>> mzip(__<M, A> ma, __<M, B> mb) {
        return mzipWith(T2::of, ma, mb);
    }
}
