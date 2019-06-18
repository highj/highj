package org.highj.data.tuple.t1;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T1;
import org.highj.typeclass0.group.Group;

import static org.highj.Hkt.asT1;

public interface T1Group<A> extends T1Monoid<A>, Group<__<T1.µ, A>> {

    @Override
    Group<A> get();

    @Override
    default T1<A> inverse(__<T1.µ, A> value) {
        return T1.of(get().inverse(asT1(value).get()));
    }
}
