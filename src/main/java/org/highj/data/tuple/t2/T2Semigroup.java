package org.highj.data.tuple.t2;

import org.derive4j.hkt.__2;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Semigroup;

import static org.highj.Hkt.asT2;

public interface T2Semigroup<A, B> extends Semigroup<__2<T2.µ, A, B>> {

    Semigroup<A> getA();

    Semigroup<B> getB();

    @Override
    default T2<A, B> apply(__2<T2.µ, A, B> x, __2<T2.µ, A, B> y) {
        T2<A, B> tx = asT2(x);
        T2<A, B> ty = asT2(y);
        return T2.of(
            getA().apply(tx._1(), ty._1()),
            getB().apply(tx._2(), ty._2()));
    }
}
