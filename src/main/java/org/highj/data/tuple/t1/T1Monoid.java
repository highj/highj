package org.highj.data.tuple.t1;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T1;
import org.highj.typeclass0.group.Monoid;

public interface T1Monoid<A> extends T1Semigroup<A>, Monoid<__<T1.µ, A>> {

    @Override
    Monoid<A> get();

    @Override
    default T1<A> identity() {
        return T1.of(get().identity());
    }
}
