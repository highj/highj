package org.highj.data.tuple.t3;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass2.bifunctor.Biapplicative;

public interface T3Biapplicative<S> extends T3Biapply<S>, Biapplicative<__<T3.µ, S>> {

    @Override
    Monoid<S> getS();

    @Override
    default <A, B> T3<S, A, B> bipure(A a, B b) {
        return T3.of(getS().identity(), a, b);
    }
}
