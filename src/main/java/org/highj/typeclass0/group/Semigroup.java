package org.highj.typeclass0.group;

import org.highj.data.List;

import java.util.function.BinaryOperator;

/**
 * A structure supporting an associative operation "apply": apply(x,apply(y,z)) == apply(apply(x,y),z)
 */
@FunctionalInterface
public interface Semigroup<A> extends BinaryOperator<A> {

    @Override
    A apply(A x, A y);

    default A fold(A a, List<A> as) {
        return as.isEmpty() ? a : fold(apply(a, as.head()), as.tail());
    }

    default A fold(A a, A... as) {
        return fold(a, List.of(as));
    }

    static <A> Semigroup<A> first() {
        return (x, y) -> x;
    }

    static <A> Semigroup<A> last() {
        return (x, y) -> y;
    }

    static <A> Semigroup<A> dual(final Semigroup<A> semigroup) {
        return (x, y) -> semigroup.apply(y, x);
    }

    static <A extends Comparable<A>> Semigroup<A> min() {
        return (x, y) -> x.compareTo(y) <= 0 ? x : y;
    }

    static <A extends Comparable<A>> Semigroup<A> max() {
        return (x, y) -> x.compareTo(y) >= 0 ? x : y;
    }
}
