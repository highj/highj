package org.highj.data.ord;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;

/**
 * An sorting order for type constructors.
 * <p>
 * Note that {@link Ord1} should be a sub class of {@link Eq1}, however this would result in a clash,
 * as both classes implement {@link org.derive4j.hkt.__}. However, you can use {@link Ord1#toEq1}
 * for converting an {@link Ord1} instance to {@link Eq1}.
 *
 * @param <F> the type of the type constructor
 */
public interface Ord1<F> extends __<Ord1.µ, F> {

    interface µ {}

    /**
     * Derives an {@link Ord} instance for an instantiated type constructor.
     *
     * @param ord an {@link Ord} instance of the element type
     * @param <A> the type of the element type
     * @return the instance
     */
    <A> Ord<__<F, A>> cmp(Ord<? super A> ord);

    /**
     * Constructs an {@link Eq1} instance from this {@link Ord1}.
     *
     * @return the {@link Eq1} instance
     */
    default Eq1<F> toEq1() {
        return new Eq1<F>() {
            @Override
            public <T> Eq<__<F, T>> eq1(Eq<? super T> eq) {
                Ord<T> fake = (one, two) -> eq.eq(one, two) ? Ordering.EQ : Ordering.LT;
                return cmp(fake)::eq;
            }
        };
    }

}
