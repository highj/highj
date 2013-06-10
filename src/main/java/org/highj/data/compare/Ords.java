package org.highj.data.compare;

import org.highj.typeclass0.compare.Ord;

import java.util.Comparator;
import java.util.function.Function;

public enum Ords {
    ;

    public static <T> Ord<T> fromComparator(final Comparator<T> comp) {
        return (one, two) -> {
            int result = comp.compare(one, two);
            return result < 0 ? Ordering.LT
                    : result > 0 ? Ordering.GT
                    : Ordering.EQ;
        };
    }

    public static <T extends Comparable<T>> Ord<T> fromComparable() {
        return (one, two) -> {
            int result = one.compareTo(two);
            return result < 0 ? Ordering.LT
                    : result > 0 ? Ordering.GT
                    : Ordering.EQ;
        };
    }

    public static <T> Ord<T>fromCompareFn(final Function<T,Function<T,Integer>> fn) {
        return (one, two) -> {
            int result = fn.apply(one).apply(two);
            return result < 0 ? Ordering.LT
                    : result > 0 ? Ordering.GT
                    : Ordering.EQ;
        };
    }

    public static <T> Ord<T> fromCmpFn(final Function<T,Function<T,Ordering>> fn) {
        return (one, two) -> fn.apply(one).apply(two);
    }

}
