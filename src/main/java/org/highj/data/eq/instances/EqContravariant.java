package org.highj.data.eq.instances;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

public interface EqContravariant extends Contravariant<Eq.µ> {
    @Override
    default <A, B> Eq<A> contramap(Function<A, B> fn, __<Eq.µ, B> nestedB) {
        return (x, y) -> Eq.narrow(nestedB).eq(fn.apply(x), fn.apply(y));
    }
}
