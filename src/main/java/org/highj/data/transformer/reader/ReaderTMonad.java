package org.highj.data.transformer.reader;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ReaderT;
import org.highj.typeclass1.monad.Monad;

/**
 * @author Clinton Selke
 */
public interface ReaderTMonad<R, M> extends ReaderTApplicative<R, M>, ReaderTBind<R, M>, Monad<__<__<ReaderT.µ, R>, M>> {

    public Monad<M> get();
}