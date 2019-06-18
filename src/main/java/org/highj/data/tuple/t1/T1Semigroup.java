package org.highj.data.tuple.t1;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T1;
import org.highj.typeclass0.group.Semigroup;

import static org.highj.Hkt.asT1;

public interface T1Semigroup<A> extends Semigroup<__<T1.µ, A>> {

    Semigroup<A> get();

    @Override
    default T1<A> apply(__<T1.µ, A> x, __<T1.µ, A> y) {
        return T1.of(get().apply(asT1(x).get(), asT1(y).get()));
    }
}
