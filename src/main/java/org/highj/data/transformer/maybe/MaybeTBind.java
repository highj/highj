package org.highj.data.transformer.maybe;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.data.transformer.MaybeT;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

/**
 * @author Clinton Selke
 */
public interface MaybeTBind<M> extends MaybeTApply<M>, Bind<__<MaybeT.µ, M>> {

    Monad<M> get();

    @Override
    default <A, B> MaybeT<M, B> bind(__<__<MaybeT.µ, M>, A> nestedA, Function<A, __<__<MaybeT.µ, M>, B>> fn) {
        __<M, Maybe<B>> m_maybeB = get().bind(
                MaybeT.narrow(nestedA).get(),
                maybeA -> maybeA.cata(
                        get().pure(Maybe.<B>Nothing()),
                        (A a) -> MaybeT.narrow(fn.apply(a)).get()
                )
        );
        return new MaybeT<>(m_maybeB);
    }
}