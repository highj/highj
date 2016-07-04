package org.highj.typeclass0.compare.ord;

import org.derive4j.hkt.__;
import org.highj.typeclass0.compare.Ord;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

import static org.highj.typeclass0.compare.Ord.*;

public interface OrdContravariant extends Contravariant<µ> {

    @Override
    default <A, B> Ord<A> contramap(Function<A, B> fn, __<µ, B> nestedB) {
        return (one, two) -> narrow(nestedB).cmp(fn.apply(one), fn.apply(two));
    }
}
