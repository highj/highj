package org.highj.typeclass2.injective;

import java.util.function.Function;

/**
 * An injective relationship.
 *
 * Law:
 * if x != y then injective.to(x) != injective.to(y)
 *
 * @param <A> source type
 * @param <B> target type
 */
public interface Injective<A, B> extends Function<A, B> {

    B to(A input);

    default B apply(A input) {
       return to(input);
    }

    static <A,B> Injective<A,B> of(Function<A,B> f) {
        return f::apply;
    }

    static <A> Injective<A,A> identity() {
        return a -> a;
    }
}
