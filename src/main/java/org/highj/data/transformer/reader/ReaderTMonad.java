package org.highj.data.transformer.reader;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.ReaderT;
import org.highj.typeclass1.monad.Monad;

/**
 * @author Clinton Selke
 */
public interface ReaderTMonad<R, M> extends ReaderTApplicative<R, M>, ReaderTBind<R, M>, Monad<_<_<ReaderT.µ, R>, M>> {

    public Monad<M> get();
}