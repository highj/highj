package org.highj.typeclass1.contravariant;


import org.highj._;

import java.util.function.Function;

/**
 * A contravariant functor.
 *
 * Mirrors Data.Functor.Contravariant.
 */
public interface Contravariant<µ> {

    // contramap (Data.Functor.Contravariant)
    public <A, B> _<µ, A> contramap(Function<A, B> fn, _<µ, B> nestedB);
}
