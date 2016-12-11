package org.highj.data.ord.instances;

import org.derive4j.hkt.__;
import org.highj.data.ord.Ord;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

import static org.highj.Hkt.asOrd;
import static org.highj.data.ord.Ord.*;

public interface OrdContravariant extends Contravariant<µ> {

    @Override
    default <A, B> Ord<A> contramap(Function<A, B> fn, __<µ, B> nestedB) {
        return (one, two) -> asOrd(nestedB).cmp(fn.apply(one), fn.apply(two));
    }
}
