package org.highj.typeclass1.contravariant;


import org.highj._;

import java.util.function.Function;

/**
 * A contravariant functor.
 *
 * Mirrors Data.Functor.Contravariant.
 */
public interface Contravariant<F> {

    // contramap (Data.Functor.Contravariant)
    public <A, B> _<F, A> contramap(Function<A, B> fn, _<F, B> nestedB);
}
