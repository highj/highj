package org.highj.data.compare.ordering;

import org.highj._;
import org.highj.typeclass0.compare.Ord;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

import static org.highj.typeclass0.compare.Ord.*;

public class OrdContravariant implements Contravariant<µ> {

    @Override
    public <A, B> Ord<A> contramap(Function<A, B> fn, _<µ, B> nestedB) {
        //  contramap f g = Comparison $ \a b -> getComparison g (f a) (f b)
        return (one, two) -> narrow(nestedB).cmp(fn.apply(one), fn.apply(two));
    }
}
