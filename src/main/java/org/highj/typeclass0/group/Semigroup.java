package org.highj.typeclass0.group;

import org.highj.data.collection.List;

/**
 * A structure supporting an associative operation "dot": dot(x,dot(y,z)) == dot(dot(x,y),z)
 */
@FunctionalInterface
public interface Semigroup<A> {

    public A dot(A x, A y);


    public default A fold(A a, List<A> as) {
        return as.isEmpty() ? a : fold(dot(a, as.head()), as.tail());
    }

    public default A fold(A a, A... as) {
        return fold(a, List.of(as));
    }

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
