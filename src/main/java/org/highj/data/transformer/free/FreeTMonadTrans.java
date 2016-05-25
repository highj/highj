package org.highj.data.transformer.free;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.transformer.FreeT;
import org.highj.typeclass1.monad.MonadTrans;

public interface FreeTMonadTrans<F,M> extends FreeTMonad<F,M>, MonadTrans<__<FreeT.µ,F>,M> {
    @Override
    default <A> __2<__<FreeT.µ, F>, M, A> lift(__<M, A> nestedA) {
        return FreeT.liftM(nestedA);
    }
}
