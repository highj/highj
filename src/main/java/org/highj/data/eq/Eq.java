package org.highj.data.eq;

import org.derive4j.hkt.__;
import org.highj.data.eq.instances.EqContravariant;
import org.highj.data.eq.instances.EqDecidable;
import org.highj.data.eq.instances.EqDivisible;
import org.highj.typeclass1.contravariant.Contravariant;
import org.highj.typeclass1.contravariant.Decidable;
import org.highj.typeclass1.contravariant.Divisible;

import java.util.function.Function;

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
     * Tests if two values are equivalent.
     *
     * @param one the first value
     * @param two the second value
     * @return true if both values are equivalent
     */
    boolean eq(T one, T two);

    /**
     * Converts this {@link Eq} instance to a contravariant one.
     *
     * @param fn  the contravariant function
     * @param <U> the element type of the new {@link Eq}
     * @return the new {@link Eq}
     */
    default <U> Eq<U> contraMap(Function<U, T> fn) {
        return contravariant.contramap(fn, this);
    }

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
     * The {@link Contravariant} instance of {@link Eq}
     */
    EqContravariant contravariant = new EqContravariant() {
    };

    /**
     * The {@link Divisible} instance of {@link Eq}
     */
    EqDivisible divisible = new EqDivisible() {
    };

    /**
     * The {@link Decidable} instance of {@link Eq}
     */
    EqDecidable decidable = new EqDecidable() {
    };
}
