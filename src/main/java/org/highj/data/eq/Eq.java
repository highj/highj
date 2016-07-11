package org.highj.data.eq;

import org.derive4j.hkt.__;
import org.highj.data.eq.instances.EqContravariant;
import org.highj.data.eq.instances.EqDecidable;
import org.highj.data.eq.instances.EqDivisible;

/**
 * Represents an equivalence relation of a type.
 *
 * @param <T> the type with equivalence relation
 */
@FunctionalInterface
public interface Eq<T> extends __<Eq.µ, T> {

    interface µ {
    }

    /**
     * Converts the higher kinded representation of an {@link Eq} back to the standard one.
     *
     * @param value the {@link Eq} in higher kinded representation
     * @param <A>   the type with equivalence relation
     * @return the {@link Eq} converted back to standard form
     */
    @SuppressWarnings("unchecked")
    static <A> Eq<A> narrow(__<µ, A> value) {
        return (Eq) value;
    }

    /**
     * Tests if two values are equivalent.
     *
     * @param one the first value
     * @param two the second value
     * @return true if both values are equivalent
     */
    boolean eq(T one, T two);

    /**
     * Constructs an {@link Eq} instance based on {@link Object#equals}.
     *
     * @param <T> the type with equivalence relation
     * @return the {@link Eq} instance
     */
    static <T> Eq<T> fromEquals() {
        return (one, two) -> one == null ? two == null : one.equals(two);
    }

    /**
     * Constructs an {@link Eq} instance based on reference equality, as defined by the == operator.
     *
     * @param <T> the type with equivalence relation
     * @return the {@link Eq} instance
     */
    static <T> Eq<T> fromObjectIdentity() {
        return (one, two) -> one == null ? two == null : one == two;
    }

    /**
     * The {@link org.highj.typeclass1.contravariant.Contravariant} instance of {@link Eq}
     */
    EqContravariant contravariant = new EqContravariant() {
    };

    /**
     * The {@link org.highj.typeclass1.contravariant.Divisible} instance of {@link Eq}
     */
    EqDivisible divisible = new EqDivisible() {
    };

    /**
     * The {@link org.highj.typeclass1.contravariant.Decidable} instance of {@link Eq}
     */
    EqDecidable decidable = new EqDecidable() {
    };
}
