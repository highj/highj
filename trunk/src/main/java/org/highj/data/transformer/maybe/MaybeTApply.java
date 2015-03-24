package org.highj.data.transformer.maybe;

import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Maybe;
import org.highj.data.transformer.MaybeT;
import org.highj.typeclass1.monad.Apply;

/**
 * @author Daniel Gronau <daniel.gronau@skillcert.de>
 */
public interface MaybeTApply<M> extends MaybeTFunctor<M>, Apply<__.µ<MaybeT.µ, M>> {

    @Override
    public Apply<M> get();

    @Override
    public default <A, B> __<MaybeT.µ, M, B> ap(_<__.µ<MaybeT.µ, M>, Function<A, B>> fn, _<__.µ<MaybeT.µ, M>, A> nestedA) {
        _<M, Maybe<A>> m_a = MaybeT.narrow(nestedA).get();
        _<M, Maybe<Function<A, B>>> m_fn = MaybeT.narrow(fn).get();
        _<M, Function<Maybe<A>, Maybe<B>>> m_fm = get().map(
                mbf -> mba -> Maybe.narrow(Maybe.monad.ap(mbf, mba)), m_fn);
        _<M, Maybe<B>> m_b = get().ap(m_fm, m_a);
        return new MaybeT<>(m_b);
    }
}
