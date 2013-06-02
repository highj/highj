package org.highj.typeclass0.group;

public enum Semigroups {
    ;

    public static <A> Semigroup<A> first() {
        return (x, y) -> x;
    }

    public static <A> Semigroup<A> last() {
        return (x, y) -> y;
    }

    public static <A> Semigroup<A> dual(final Semigroup<A> semigroup) {
        return (x, y) -> semigroup.dot(y, x);
    }

    public static <A extends Comparable<A>> Semigroup<A> min() {
        return (x, y) -> x.compareTo(y) <= 0 ? x : y;
    }

    public static <A extends Comparable<A>> Semigroup<A> max() {
        return (x, y) -> x.compareTo(y) >= 0 ? x : y;
    }
}
