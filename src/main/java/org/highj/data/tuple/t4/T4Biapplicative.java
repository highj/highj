package org.highj.data.tuple.t4;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass2.bifunctor.Biapplicative;

public interface T4Biapplicative<S, T> extends T4Biapply<S, T>, Biapplicative<__<__<T4.µ, S>, T>> {
    @Override
    Monoid<S> getS();

    @Override
    Monoid<T> getT();

    @Override
    default <A, B> T4<S, T, A, B> bipure(A a, B b) {
        return T4.of(getS().identity(), getT().identity(), a, b);
    }
}
