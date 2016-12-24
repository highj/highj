package org.highj.data.eq;

import org.derive4j.hkt.__;

/**
 * An equivalence relation for type constructors.
 *
 * @param <F> the type constructor
 */
public interface Eq1<F> {
    /**
     * Derives an {@link Eq} instance for an instantiated type constructor.
     * @param eq an {@link Eq} instance of the element type
     * @param <T> the type of the element type
     * @return the instance
     */
    <T> Eq<__<F, T>> eq1(Eq<? super T> eq);
}
