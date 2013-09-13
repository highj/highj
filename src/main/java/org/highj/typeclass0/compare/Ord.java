package org.highj.typeclass0.compare;

import org.highj._;
import org.highj.data.compare.Ordering;

import java.util.Comparator;
import java.util.function.Function;

@FunctionalInterface
public interface Ord<T> extends Comparator<T>, Eq<T>, Function<T,Function<T,Ordering>>, _<Ord.µ,T> {

    public static class µ {}

    @Override
    public default Function<T, Ordering> apply(T x) {
        return y -> cmp(x,y);
    }

    @SuppressWarnings("unchecked")
    public static <A> Ord<A> narrow(_<µ,A> nestedA) {
        return (Ord) nestedA;
    }

    public static <A> Ord<A> fromComparator(Comparator<A> comparator) {
        return (one, two) -> {
            int result = comparator.compare(one, two);
            if (result < 0) {
                return Ordering.LT;
            } else if (result == 0) {
                return Ordering.EQ;
            } else {
                return Ordering.GT;
            }
        };
    }

    public static <A extends Comparable<? super A>> Ord<A> fromComparable() {
        return (one, two) -> {
            if (one == null && two == null) {
                return Ordering.EQ;
            } else if (one == null) {
                return Ordering.LT;
            } else if (two == null) {
                return Ordering.GT;
            }  else {
                int result = one.compareTo(two);
                if (result < 0) {
                    return Ordering.LT;
                } else if (result == 0) {
                    return Ordering.EQ;
                } else {
                    return Ordering.GT;
                }
            }
        };
    }

    public abstract Ordering cmp(T one, T two);

    @Override
    public default int compare(T one, T two) {
        return cmp(one, two).cmpResult();
    }

    @Override
    public default boolean eq(T one, T two) {
        return cmp(one, two) == Ordering.EQ;
    }

    public default boolean less(T one, T two) {
        return cmp(one, two) == Ordering.LT;
    }

    public default boolean lessEqual(T one, T two) {
        return cmp(one, two) != Ordering.GT;
    }

    public default boolean greater(T one, T two) {
        return cmp(one, two) == Ordering.GT;
    }

    public default boolean greaterEqual(T one, T two) {
        return cmp(one, two) != Ordering.LT;
    }

    public default T min(T one, T two) {
        return lessEqual(one, two) ? one : two;
    }

    public default T max(T one, T two) {
        return greaterEqual(one, two) ? one : two;
    }
}
