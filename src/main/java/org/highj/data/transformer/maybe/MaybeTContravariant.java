package org.highj.data.transformer.maybe;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.data.transformer.MaybeT;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

import static org.highj.Hkt.asMaybeT;

public interface MaybeTContravariant<M> extends Contravariant<__<MaybeT.µ, M>> {

    Contravariant<M> get();

    @Override
    default <A, B> MaybeT<M, A> contramap(Function<A, B> fn, __<__<MaybeT.µ, M>, B> nestedB) {
        return new MaybeT<>(get().contramap(Maybe.lift(fn), asMaybeT(nestedB).get()));
    }
}
