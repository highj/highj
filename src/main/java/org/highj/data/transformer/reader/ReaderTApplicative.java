package org.highj.data.transformer.reader;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.ReaderT;
import org.highj.typeclass1.monad.Applicative;

/**
 * @author Clinton Selke
 */
public interface ReaderTApplicative<R, M> extends ReaderTApply<R, M>, Applicative<_<_<ReaderT.µ, R>, M>> {

    public Applicative<M> get();

    @Override
    public default <A> ReaderT<R, M, A> pure(A a) {
        return (R r) -> get().pure(a);
    }
}