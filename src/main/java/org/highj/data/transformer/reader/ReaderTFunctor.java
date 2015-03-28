package org.highj.data.transformer.reader;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.ReaderT;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

/**
 * @author Clinton Selke
 */
public interface ReaderTFunctor<R, M> extends Functor<__.µ<___.µ<ReaderT.µ, R>, M>> {

    public Functor<M> get();

    @Override
    public default <A, B> ReaderT<R, M, B> map(Function<A, B> fn, _<__.µ<___.µ<ReaderT.µ, R>, M>, A> nestedA) {
        return (R r) -> get().map(
                fn,
                ReaderT.narrow(nestedA).run(r)
        );
    }
}