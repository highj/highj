package org.highj.data.compare;

import java.util.Comparator;

public interface Ord<T> extends Comparator<T>, Eq<T> {

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
