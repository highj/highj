package org.highj.data.predicates.pred;

import org.derive4j.hkt.__;
import org.highj.data.predicates.Pred;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

public class PredContravariant implements Contravariant<Pred.µ> {
    @Override
    public <A, B> Pred<A> contramap(Function<A, B> fn, __<Pred.µ, B> nestedB) {
        // contramap f g = Predicate $ getPredicate g . f
        return a -> Pred.narrow(nestedB).test(fn.apply(a));
    }
}
