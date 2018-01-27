package org.highj.data.ord;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;
import org.highj.data.ord.instances.Ord1Contravariant1;
import org.highj.function.NF;
import org.highj.typeclass1.contravariant.Contravariant1;

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

    interface µ {
    }

    /**
     * Derives an {@link Ord} instance for an instantiated type constructor.
     *
     * @param ord an {@link Ord} instance of the element type
     * @param <A> the type of the element type
     * @return the instance
     */
    <A> Ord<__<F, A>> cmp(Ord<? super A> ord);

    default <E> Ord1<E> contramap(NF<E, F> f1) {
        return new Ord1<E>() {
            @Override
            public <T> Ord<__<E, T>> cmp(Ord<? super T> ord) {
                Ord<__<F, T>> ord1 = Ord1.this.cmp(ord);
                return (one, two) -> ord1.cmp(f1.apply(one), f1.apply(two));
            }
        };
    }

    /**
     * The {@link Contravariant1} instance of {@link Ord1}
     */
    Ord1Contravariant1 contravariant1 = new Ord1Contravariant1() {
    };

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
