package org.highj.data.transformer.writer;

import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.WriterT;
import org.highj.typeclass1.monad.Monad;

/**
 * @author Clinton Selke
 */
public interface WriterTMonad<W, M> extends WriterTApplicative<W, M>, WriterTBind<W, M>, Monad<__.µ<___.µ<WriterT.µ, W>, M>> {

    public Monad<M> get();
}
