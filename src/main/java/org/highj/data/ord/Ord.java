package org.highj.data.ord;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.highj.data.ord.instances.OrdContravariant;

import java.util.Comparator;
import java.util.function.Function;

@FunctionalInterface
public interface Ord<T> extends Comparator<T>, Eq<T>, Function<T,Function<T,Ordering>>, __<Ord.µ,T> {

    interface µ {}

    @Override
    default Function<T, Ordering> apply(T x) {
        return y -> cmp(x,y);
    }

    @SuppressWarnings("unchecked")
    static <A> Ord<A> narrow(__<µ,A> nestedA) {
        return (Ord) nestedA;
    }

    static <A> Ord<A> fromComparator(Comparator<A> comparator) {
        return comparator instanceof Ord
                       ? (Ord<A>) comparator
                       : (one, two) -> Ordering.fromInt(comparator.compare(one, two));
    }

    static <A extends Comparable<? super A>> Ord<A> fromComparable() {
        return Ordering::compare;
    }

    Ordering cmp(T one, T two);

    @Override
    default int compare(T one, T two) {
        return cmp(one, two).cmpResult();
    }

    @Override
    default boolean eq(T one, T two) {
        return cmp(one, two) == Ordering.EQ;
    }

    default boolean less(T one, T two) {
        return cmp(one, two) == Ordering.LT;
    }

    default boolean lessEqual(T one, T two) {
        return cmp(one, two) != Ordering.GT;
    }

    default boolean greater(T one, T two) {
        return cmp(one, two) == Ordering.GT;
    }

    default boolean greaterEqual(T one, T two) {
        return cmp(one, two) != Ordering.LT;
    }

    default T min(T one, T two) {
        return lessEqual(one, two) ? one : two;
    }

    default T max(T one, T two) {
        return greaterEqual(one, two) ? one : two;
    }

    OrdContravariant contravariant = new OrdContravariant(){};
}
