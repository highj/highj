package org.highj.data.tuple.t2;

import org.highj.data.tuple.T2;
import org.highj.typeclass2.bifunctor.Biapplicative;

public interface T2Biapplicative extends T2Biapply, Biapplicative<T2.µ> {
    @Override
    default <A, B> T2<A, B> bipure(A a, B b) {
        return T2.of(a,b);
    }
}
