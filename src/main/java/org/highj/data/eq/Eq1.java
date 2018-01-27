package org.highj.data.eq;

import org.derive4j.hkt.__;
import org.highj.data.eq.instances.Eq1Contravariant1;
import org.highj.function.NF;
import org.highj.typeclass1.contravariant.Contravariant1;

/**
 * An equivalence relation for type constructors.
 *
 * @param <F> the type of the type constructor
 */
public interface Eq1<F> extends __<Eq1.µ, F> {

    interface µ {
    }

    /**
     * Derives an {@link Eq} instance for an instantiated type constructor.
     *
     * @param eq  an {@link Eq} instance of the element type
     * @param <T> the type of the element type
     * @return the instance
     */
    <T> Eq<__<F, T>> eq1(Eq<? super T> eq);

    default <E> Eq1<E> contramap(NF<E, F> f1) {
        return new Eq1<E>() {
            @Override
            public <T> Eq<__<E, T>> eq1(Eq<? super T> eq) {
                Eq<__<F, T>> eq1 = Eq1.this.eq1(eq);
                return (one, two) -> eq1.eq(f1.apply(one), f1.apply(two));
            }
        };
    }

    /**
     * The {@link Contravariant1} instance of {@link Eq1}
     */
    Eq1Contravariant1 contravariant1 = new Eq1Contravariant1() {
    };
}
