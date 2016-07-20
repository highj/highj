package org.highj.data.transformer.writer;

import org.derive4j.hkt.__;
import org.highj.data.transformer.WriterT;
import org.highj.typeclass1.monad.Monad;

/**
 * @author Clinton Selke
 */
public interface WriterTMonad<W, M> extends WriterTApplicative<W, M>, WriterTBind<W, M>, Monad<__<__<WriterT.µ, W>, M>> {

    Monad<M> getM();
}
