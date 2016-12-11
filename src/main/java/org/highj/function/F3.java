package org.highj.function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__4;

import java.util.function.Function;

public interface F3<A, B, C, D> extends __4<F3.µ, A, B, C, D> {

    class µ {
    }

    D apply(A a, B b, C c);

    default F1<C, D> apply(A a, B b) {
        return c -> apply(a, b, c);
    }

    default F2<B, C, D> apply(A a) {
        return (b, c) -> apply(a, b, c);
    }

    default <V> F3<A, B, C, V> andThen(Function<? super D, ? extends V> after) {
        return (a, b, c) -> after.apply(apply(a, b, c));
    }

    default F1<A, F1<B, F1<C, D>>> curry() {
        return a -> b -> c -> apply(a, b, c);
    }

}
