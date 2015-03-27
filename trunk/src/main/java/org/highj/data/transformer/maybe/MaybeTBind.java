package org.highj.data.transformer.maybe;

import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Maybe;
import org.highj.data.transformer.MaybeT;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;

/**
 * @author Clinton Selke
 */
public interface MaybeTBind<M> extends MaybeTApply<M>, Bind<__.µ<MaybeT.µ, M>> {

    public Monad<M> get();

    @Override
    public default <A, B> MaybeT<M, B> bind(_<__.µ<MaybeT.µ, M>, A> nestedA, Function<A, _<__.µ<MaybeT.µ, M>, B>> fn) {
        return new MaybeT<>(
                get().bind(
                        MaybeT.narrow(nestedA).get(),
                        (Maybe<A> a) -> a.cata(
                                get().pure(Maybe.<B>Nothing()),
                                (A a2) -> MaybeT.narrow(fn.apply(a2)).get()
                        )
                )
        );
    }
}