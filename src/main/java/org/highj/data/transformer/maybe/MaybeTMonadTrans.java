package org.highj.data.transformer.maybe;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.data.transformer.MaybeT;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadTrans;

/**
 * @author Clinton Selke
 */
public interface MaybeTMonadTrans<M> extends MaybeTMonad<M>, MonadTrans<MaybeT.µ, M> {

    Monad<M> get();

    @Override
    default <A> MaybeT<M, A> lift(__<M, A> nestedA) {
        return new MaybeT<>(get().map(Maybe::Just, nestedA));
    }
}