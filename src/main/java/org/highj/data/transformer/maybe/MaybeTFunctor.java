package org.highj.data.transformer.maybe;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.data.transformer.MaybeT;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asMaybeT;

/**
 * @author Daniel Gronau
 */
public interface MaybeTFunctor<M> extends Functor<__<MaybeT.µ, M>> {

    Functor<M> get();

    @Override
    public default <A, B> MaybeT<M, B> map(Function<A, B> fn, __<__<MaybeT.µ, M>, A> nestedA) {
        __<M, Maybe<A>> m_a = asMaybeT(nestedA).get();
        __<M, Maybe<B>> m_b = get().map(ma -> ma.map(fn), m_a);
        return new MaybeT<>(m_b);
    }
}
