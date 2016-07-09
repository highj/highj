package org.highj.data.predicates.pred;

import org.derive4j.hkt.__;
import org.highj.data.predicates.Pred;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

public interface PredContravariant extends Contravariant<Pred.µ> {
    @Override
    default <A, B> Pred<A> contramap(Function<A, B> fn, __<Pred.µ, B> nestedB) {
        return a -> Pred.narrow(nestedB).test(fn.apply(a));
    }
}
