package org.highj.data.transformer.reader;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.ReaderT;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadReader;

import java.util.function.Function;

/**
 * @author Clinton Selke
 */
public interface ReaderTMonadReader<R, M> extends ReaderTMonad<R, M>, MonadReader<R, __.µ<___.µ<ReaderT.µ, R>, M>> {

    @Override
    public Monad<M> get();

    @Override
    public default ReaderT<R, M, R> ask() {
        return (R r) -> get().pure(r);
    }

    @Override
    public default <A> ReaderT<R, M, A> local(Function<R, R> modFn, _<__.µ<___.µ<ReaderT.µ, R>, M>, A> nestedA) {
        return (R r) -> ReaderT.narrow(nestedA).run(modFn.apply(r));
    }
}