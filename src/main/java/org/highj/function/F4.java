package org.highj.function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__5;

import java.util.function.Function;

public interface F4<A, B, C, D, E> extends __5<F4.µ, A, B, C, D, E> {

    class µ {
    }

    E apply(A a, B b, C c, D d);

    default F1<D, E> apply(A a, B b, C c) {
        return d -> apply(a, b, c, d);
    }

    default F2<C, D, E> apply(A a, B b) {
        return (c, d) -> apply(a, b, c, d);
    }

    default F3<B, C, D, E> apply(A a) {
        return (b, c, d) -> apply(a, b, c, d);
    }

    default <V> F4<A, B, C, D, V> andThen(Function<? super E, ? extends V> after) {
        return (a, b, c, d) -> after.apply(apply(a, b, c, d));
    }

    default F1<A, F1<B, F1<C, F1<D, E>>>> curry() {
        return a -> b -> c -> d -> apply(a, b, c, d);
    }

}
