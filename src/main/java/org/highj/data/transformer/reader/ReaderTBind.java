package org.highj.data.transformer.reader;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ReaderT;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

/**
 * @author Clinton Selke
 */
public interface ReaderTBind<R, M> extends ReaderTApply<R, M>, Bind<__<__<ReaderT.µ, R>, M>> {

    public Bind<M> get();

    @Override
    public default <A, B> ReaderT<R, M, B> bind(__<__<__<ReaderT.µ, R>, M>, A> nestedA, Function<A, __<__<__<ReaderT.µ, R>, M>, B>> fn) {
        return (R r) -> get().bind(
                ReaderT.narrow(nestedA).run(r),
                (A a) -> ReaderT.narrow(fn.apply(a)).run(r)
        );
    }
}
