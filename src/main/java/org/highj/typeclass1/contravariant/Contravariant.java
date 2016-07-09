package org.highj.typeclass1.contravariant;


import org.derive4j.hkt.__;
import org.highj.typeclass1.invariant.Invariant;

import java.util.function.Function;

/**
 * A contravariant functor.
 *
 * Mirrors Data.Functor.Contravariant.
 *
 * @param <F> the Contravariant instance
 */
public interface Contravariant<F> extends Invariant<F> {

    // contramap (Data.Functor.Contravariant)
    <A, B> __<F, A> contramap(Function<A, B> fn, __<F, B> nestedB);

    default <A, B> __<F, B> invmap(Function<A, B> fn, Function<B,A> nf, __<F, A> nestedA) {
        return contramap(nf, nestedA);
    }
}
