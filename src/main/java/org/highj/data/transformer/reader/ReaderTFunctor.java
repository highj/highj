package org.highj.data.transformer.reader;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ReaderT;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asReaderT;

/**
 * @author Clinton Selke
 */
public interface ReaderTFunctor<R, M> extends Functor<__<__<ReaderT.µ, R>, M>> {

    public Functor<M> get();

    @Override
    public default <A, B> ReaderT<R, M, B> map(Function<A, B> fn, __<__<__<ReaderT.µ, R>, M>, A> nestedA) {
        return (R r) -> get().map(
                fn,
                asReaderT(nestedA).run(r)
        );
    }
}