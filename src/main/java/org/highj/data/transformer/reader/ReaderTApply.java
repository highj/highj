package org.highj.data.transformer.reader;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ReaderT;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.Hkt.asReaderT;

/**
 * @author Clinton Selke
 */
public interface ReaderTApply<R, M> extends ReaderTFunctor<R, M>, Apply<__<__<ReaderT.µ, R>, M>> {

    public Apply<M> get();

    @Override
    public default <A, B> ReaderT<R, M, B> ap(__<__<__<ReaderT.µ, R>, M>, Function<A, B>> fn, __<__<__<ReaderT.µ, R>, M>, A> nestedA) {
        return (R r) -> get().ap(
                asReaderT(fn).run(r),
                asReaderT(nestedA).run(r)
        );
    }
}