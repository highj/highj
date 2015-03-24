package org.highj.data.transformer.maybe;

import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Maybe;
import org.highj.data.transformer.MaybeT;
import org.highj.typeclass1.functor.Functor;

/**
 * @author Daniel Gronau <daniel.gronau@skillcert.de>
 */
public interface MaybeTFunctor<M> extends Functor<__.µ<MaybeT.µ, M>> {

    Functor<M> get();

    @Override
    public default <A, B> __<MaybeT.µ, M, B> map(Function<A, B> fn, _<__.µ<MaybeT.µ, M>, A> nestedA) {
        _<M, Maybe<A>> m_a = MaybeT.narrow(nestedA).get();
        _<M, Maybe<B>> m_b = get().map(ma -> ma.map(fn), m_a);
        return new MaybeT<>(m_b);
    }
}
