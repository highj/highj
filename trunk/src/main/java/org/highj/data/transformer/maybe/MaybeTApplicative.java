package org.highj.data.transformer.maybe;

import org.highj.__;
import org.highj.data.collection.Maybe;
import org.highj.data.transformer.MaybeT;
import org.highj.typeclass1.monad.Applicative;

/**
 * @author Daniel Gronau <daniel.gronau@skillcert.de>
 */
public interface MaybeTApplicative<M> extends MaybeTApply<M>, Applicative<__.µ<MaybeT.µ, M>> {

    @Override
    public Applicative<M> get();

    @Override
    public default <A> __<MaybeT.µ, M, A> pure(A a) {
        return new MaybeT<>(get().pure(Maybe.monad.pure(a)));
    }

}