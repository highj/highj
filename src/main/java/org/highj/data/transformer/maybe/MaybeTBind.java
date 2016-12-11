package org.highj.data.transformer.maybe;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.data.transformer.MaybeT;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

import static org.highj.Hkt.asMaybeT;

/**
 * @author Clinton Selke
 */
public interface MaybeTBind<M> extends MaybeTApply<M>, Bind<__<MaybeT.µ, M>> {

    Monad<M> get();

    @Override
    default <A, B> MaybeT<M, B> bind(__<__<MaybeT.µ, M>, A> nestedA, Function<A, __<__<MaybeT.µ, M>, B>> fn) {
        __<M, Maybe<B>> m_maybeB = get().bind(
                asMaybeT(nestedA).get(),
                maybeA -> maybeA.cata(
                        get().pure(Maybe.<B>Nothing()),
                        (A a) -> asMaybeT(fn.apply(a)).get()
                )
        );
        return new MaybeT<>(m_maybeB);
    }
}