package org.highj.data.transformer.maybe;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.data.transformer.MaybeT;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

/**
 * @author Daniel Gronau
 * @author Clinton Selke
 */
public interface MaybeTApply<M> extends MaybeTFunctor<M>, Apply<__<MaybeT.µ, M>> {

    @Override
    public Apply<M> get();

    @Override
    public default <A, B> MaybeT<M, B> ap(__<__<MaybeT.µ, M>, Function<A, B>> fn, __<__<MaybeT.µ, M>, A> nestedA) {
        __<M, Maybe<A>> m_a = MaybeT.narrow(nestedA).get();
        __<M, Maybe<Function<A, B>>> m_fn = MaybeT.narrow(fn).get();
        __<M, Function<Maybe<A>, Maybe<B>>> m_fm = get().map(
                mbf -> mba -> Maybe.narrow(Maybe.monad.ap(mbf, mba)), m_fn);
        __<M, Maybe<B>> m_b = get().ap(m_fm, m_a);
        return new MaybeT<>(m_b);
    }
}
