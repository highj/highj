package org.highj.typeclass0.group;

import org.highj.data.List;

import java.util.function.BinaryOperator;

/**
 * A structure supporting an associative operation "apply": apply(x,apply(y,z)) == apply(apply(x,y),z)
 */
@FunctionalInterface
public interface Semigroup<A> extends BinaryOperator<A> {

    @Override
    public A apply(A x, A y);

    public default A fold(A a, List<A> as) {
        return as.isEmpty() ? a : fold(apply(a, as.head()), as.tail());
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
        return (x, y) -> semigroup.apply(y, x);
    }

    public static <A extends Comparable<A>> Semigroup<A> min() {
        return (x, y) -> x.compareTo(y) <= 0 ? x : y;
    }

    public static <A extends Comparable<A>> Semigroup<A> max() {
        return (x, y) -> x.compareTo(y) >= 0 ? x : y;
    }
}
