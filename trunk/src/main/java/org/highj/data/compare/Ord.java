package org.highj.data.compare;

import org.highj.function.F2;

import java.util.Comparator;

public abstract class Ord<T> implements Comparator<T>, Eq<T> {

    public abstract Ordering cmp(T one, T two);

    public final F2<T, T, Ordering> cmp = new F2<T, T, Ordering>() {
        @Override
        public Ordering $(T one, T two) {
            return cmp(one, two);
        }
    };

    public final F2<T, T, T> min = new F2<T, T, T>() {

        @Override
        public T $(T x, T y) {
            return min(x, y);
        }
    };

    public final F2<T, T, T> max = new F2<T, T, T>() {

        @Override
        public T $(T x, T y) {
            return max(x, y);
        }
    };

    @Override
    public int compare(T one, T two) {
        return cmp(one, two).cmpResult();
    }

    @Override
    public boolean eq(T one, T two) {
        return cmp(one, two) == Ordering.EQ;
    }

    public boolean less(T one, T two) {
        return cmp(one, two) == Ordering.LT;
    }

    public boolean lessEqual(T one, T two) {
        return cmp(one, two) != Ordering.GT;
    }

    public boolean greater(T one, T two) {
        return cmp(one, two) == Ordering.GT;
    }

    public boolean greaterEqual(T one, T two) {
        return cmp(one, two) != Ordering.LT;
    }

    public T min(T one, T two) {
        return lessEqual(one, two) ? one : two;
    }

    public T max(T one, T two) {
        return greaterEqual(one, two) ? one : two;
    }

    public static <T> Ord<T> fromComparator(final Comparator<T> comp) {
        return new Ord<T>() {
            @Override
            public Ordering cmp(T one, T two) {
                int result = comp.compare(one, two);
                return result < 0 ? Ordering.LT
                        : result > 0 ? Ordering.GT
                        : Ordering.EQ;
            }
        };
    }

    public static <T extends Comparable<T>> Ord<T> fromComparable() {
        return new Ord<T>() {
            @Override
            public Ordering cmp(T one, T two) {
                int result = one.compareTo(two);
                return result < 0 ? Ordering.LT
                        : result > 0 ? Ordering.GT
                        : Ordering.EQ;
            }
        };
    }

    public static <T> Ord<T>fromCompareFn(final F2<T,T,Integer> fn) {
        return new Ord<T>() {

            @Override
            public Ordering cmp(T one, T two) {
                int result = fn.$(one, two);
                return result < 0 ? Ordering.LT
                        : result > 0 ? Ordering.GT
                        : Ordering.EQ;
            }
        };
    }

    public static <T> Ord<T>fromCmpFn(final F2<T,T,Ordering> fn) {
        return new Ord<T>() {

            @Override
            public Ordering cmp(T one, T two) {
                return fn.$(one, two);
            }
        };
    }

}
