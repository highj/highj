package org.highj.data.transformer.writer;

import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.WriterT;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

/**
 * @author Clinton Selke
 */
public interface WriterTApplicative<W, M> extends WriterTApply<W, M>, Applicative<__.µ<___.µ<WriterT.µ, W>, M>> {

    public Monoid<W> wMonoid();

    public Applicative<M> get();

    @Override
    public default <A> WriterT<W, M, A> pure(A a) {
        return () -> get().pure(T2.of(a, wMonoid().identity()));
    }
}
