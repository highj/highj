package org.highj.data.transformer.error;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ErrorT;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

import static org.highj.Hkt.asErrorT;

public interface ErrorTContravariant<E, M> extends Contravariant<__<__<ErrorT.µ, E>, M>> {

    Contravariant<M> get();

    @Override
    default <A, B> ErrorT<E, M, A> contramap(Function<A, B> fn, __<__<__<ErrorT.µ, E>, M>, B> nestedB) {
        return () -> get().contramap(either -> either.rightMap(fn), asErrorT(nestedB).run());
    }
}
