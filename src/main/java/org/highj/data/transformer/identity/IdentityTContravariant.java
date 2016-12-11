package org.highj.data.transformer.identity;

import org.derive4j.hkt.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

import static org.highj.Hkt.asIdentityT;

public interface IdentityTContravariant<M> extends Contravariant<__<IdentityT.µ, M>> {

    Contravariant<M> get();

    @Override
    default <A, B> IdentityT<M, A> contramap(Function<A, B> fn, __<__<IdentityT.µ, M>, B> nestedB) {
        return new IdentityT<>(get().contramap(fn, asIdentityT(nestedB).get()));
    }
}
