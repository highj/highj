package org.highj.data.transformer.writer;

import org.derive4j.hkt.__;
import org.highj.data.transformer.WriterT;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

/**
 * @author Clinton Selke
 */
public interface WriterTApplicative<W, M> extends WriterTApply<W, M>, Applicative<__<__<WriterT.µ, W>, M>> {

    Monoid<W> getW();

    Applicative<M> getM();

    @Override
    default <A> WriterT<W, M, A> pure(A a) {
        return () -> getM().pure(T2.of(a, getW().identity()));
    }
}
