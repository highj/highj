package org.highj.typeclass1.contravariant;


import org.highj._;
import org.highj.typeclass1.invariant.Invariant;

import java.util.function.Function;

/**
 * A contravariant functor.
 *
 * Mirrors Data.Functor.Contravariant.
 */
public interface Contravariant<F> extends Invariant<F> {

    // contramap (Data.Functor.Contravariant)
    public <A, B> _<F, A> contramap(Function<A, B> fn, _<F, B> nestedB);

    public default <A, B> _<F, B> invmap(Function<A, B> fn, Function<B,A> nf, _<F, A> nestedA) {
        return contramap(nf, nestedA);
    }
}
