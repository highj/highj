package org.highj.data.transformer.maybe;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.data.transformer.MaybeT;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.Hkt.asMaybe;
import static org.highj.Hkt.asMaybeT;

/**
 * @author Daniel Gronau
 * @author Clinton Selke
 */
public interface MaybeTApply<M> extends MaybeTFunctor<M>, Apply<__<MaybeT.µ, M>> {

    @Override
    public Apply<M> get();

    @Override
    public default <A, B> MaybeT<M, B> ap(__<__<MaybeT.µ, M>, Function<A, B>> fn, __<__<MaybeT.µ, M>, A> nestedA) {
        __<M, Maybe<A>> m_a = asMaybeT(nestedA).get();
        __<M, Maybe<Function<A, B>>> m_fn = asMaybeT(fn).get();
        __<M, Function<Maybe<A>, Maybe<B>>> m_fm = get().map(
                mbf -> mba -> asMaybe(Maybe.monad.ap(mbf, mba)), m_fn);
        __<M, Maybe<B>> m_b = get().ap(m_fm, m_a);
        return new MaybeT<>(m_b);
    }
}
