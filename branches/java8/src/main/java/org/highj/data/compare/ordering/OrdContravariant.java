package org.highj.data.compare.ordering;

import org.highj._;
import org.highj.data.compare.Ord;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

import static org.highj.data.compare.Ord.*;

public class OrdContravariant implements Contravariant<µ> {

    @Override
    public <A, B> _<µ, A> contramap(Function<A, B> fn, _<µ, B> nestedB) {
        //  contramap f g = Comparison $ \a b -> getComparison g (f a) (f b)
        return (Ord<A>) (one, two) -> narrow(nestedB).cmp(fn.apply(one), fn.apply(two));
    }
}
