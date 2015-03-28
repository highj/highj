package org.highj.data.transformer.reader;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.ReaderT;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

/**
 * @author Clinton Selke
 */
public interface ReaderTApply<R, M> extends ReaderTFunctor<R, M>, Apply<__.µ<___.µ<ReaderT.µ, R>, M>> {

    public Apply<M> get();

    @Override
    public default <A, B> ReaderT<R, M, B> ap(_<__.µ<___.µ<ReaderT.µ, R>, M>, Function<A, B>> fn, _<__.µ<___.µ<ReaderT.µ, R>, M>, A> nestedA) {
        return (R r) -> get().ap(
                ReaderT.narrow(fn).run(r),
                ReaderT.narrow(nestedA).run(r)
        );
    }
}