package org.highj.data.ord;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.highj.data.ord.instances.OrdContravariant;
import org.highj.data.ord.instances.OrdDecidable;
import org.highj.data.ord.instances.OrdDivisible;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Represents a sorting order of a given type.
 * <p>
 * This class is similar to {@link Comparable}, however it uses {@link Ordering} as result type
 * instead of abusing int values for this.
 * <p>
 * Note that {@link Ord} should be a sub class of {@link Eq}, however this would result in a clash,
 * as both classes implement {@link org.derive4j.hkt.__}. However, you can use {@link Ord#toEq}
 * for converting an {@link Ord} instance to {@link Eq}.
 *
 * @param <T> the ordered type
 */
@FunctionalInterface
public interface Ord<T> extends Comparator<T>, Function<T, Function<T, Ordering>>, __<Ord.µ, T> {

    interface µ {
    }

    @Override
    default Function<T, Ordering> apply(T x) {
        return y -> cmp(x, y);
    }

    /**
     * Constructs an {@link Ord} from a {@link Comparator}.
     * <p>
     * If the given {@link Comparator} is already an {@link Ord}, it will be just casted.
     *
     * @param comparator the comparator
     * @param <A>        the ordered type
     * @return the {@link Ord} instance
     */
    static <A> Ord<A> fromComparator(Comparator<A> comparator) {
        return comparator instanceof Ord
                ? (Ord<A>) comparator
                : (one, two) -> Ordering.fromInt(comparator.compare(one, two));
    }

    /**
     * Constructs an {@link Ord} for the natural order of a type (as defined by {@link Comparable}).
     *
     * @param <A> the ordered type
     * @return the {@link Ord} instance
     */
    static <A extends Comparable<? super A>> Ord<A> fromComparable() {
        return Ordering::compare;
    }

    /**
     * Compares to values.
     *
     * @param one the first value
     * @param two the second value
     * @return the comparison result
     */
    Ordering cmp(T one, T two);

    @Override
    default int compare(T one, T two) {
        return cmp(one, two).cmpResult();
    }

    /**
     * Tests if both values are equal.
     *
     * @param one the first value
     * @param two the second value
     * @return true if both are equal
     */
    default boolean eq(T one, T two) {
        return cmp(one, two) == Ordering.EQ;
    }

    /**
     * Tests if the first value is smaller than the second one.
     *
     * @param one the first value
     * @param two the second value
     * @return true if the first value is smaller
     */
    default boolean less(T one, T two) {
        return cmp(one, two) == Ordering.LT;
    }

    /**
     * Tests if the first value is smaller than or equal to the second one.
     *
     * @param one the first value
     * @param two the second value
     * @return true if the first value is smaller or equal
     */
    default boolean lessEqual(T one, T two) {
        return cmp(one, two) != Ordering.GT;
    }

    /**
     * Tests if the first value is greater than the second one.
     *
     * @param one the first value
     * @param two the second value
     * @return true if the first value is greater
     */
    default boolean greater(T one, T two) {
        return cmp(one, two) == Ordering.GT;
    }

    /**
     * Tests if the first value is greater than or equal to the second one.
     *
     * @param one the first value
     * @param two the second value
     * @return true if the first value is greater or equal
     */
    default boolean greaterEqual(T one, T two) {
        return cmp(one, two) != Ordering.LT;
    }

    /**
     * Returns the smaller of two values.
     *
     * @param one the first value
     * @param two the second value
     * @return the smaller value
     */
    default T min(T one, T two) {
        return lessEqual(one, two) ? one : two;
    }

    /**
     * Returns the greater of two values.
     *
     * @param one the first value
     * @param two the second value
     * @return the greater value
     */
    default T max(T one, T two) {
        return greaterEqual(one, two) ? one : two;
    }

    /**
     * Constructs an {@link Eq} instance from this {@link Ord}.
     *
     * @return the {@link Eq} instance
     */
    default Eq<T> toEq() {
        return this::eq;
    }

    /**
     * Constructs an {@link Ord} with reversed order.
     * @return the new {@link Ord}
     */
    @Override
    default Ord<T> reversed() {
        return (x,y) -> Ord.this.cmp(y,x);
    }

    /**
     * The {@link org.highj.typeclass1.contravariant.Contravariant} instance of {@link Ord}
     */
    OrdContravariant contravariant = new OrdContravariant() {
    };

    /**
     * The {@link org.highj.typeclass1.contravariant.Divisible} instance of {@link Ord}
     */
    OrdDivisible divisible = new OrdDivisible() {
    };

    /**
     * The {@link org.highj.typeclass1.contravariant.Decidable} instance of {@link Ord}
     */
    OrdDecidable decidable = new OrdDecidable() {
    };
}
