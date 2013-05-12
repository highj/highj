package org.highj.data.predicates.pred;

import org.highj._;
import org.highj.data.predicates.Pred;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

public class PredContravariant implements Contravariant<Pred.µ> {
    @Override
    public <A, B> _<Pred.µ, A> contramap(Function<A, B> fn, _<Pred.µ, B> nestedB) {
        // contramap f g = Predicate $ getPredicate g . f
        return (Pred<A>) a -> Pred.narrow(nestedB).test(fn.apply(a));
    }
}
