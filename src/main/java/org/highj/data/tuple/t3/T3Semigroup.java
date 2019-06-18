package org.highj.data.tuple.t3;

import org.derive4j.hkt.__3;
import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Semigroup;

import static org.highj.Hkt.asT3;

public interface T3Semigroup<A, B, C> extends Semigroup<__3<T3.µ, A, B, C>> {

    Semigroup<A> getA();

    Semigroup<B> getB();

    Semigroup<C> getC();

    @Override
    default T3<A, B, C> apply(__3<T3.µ, A, B, C> x, __3<T3.µ, A, B, C> y) {
        T3<A, B, C> tx = asT3(x);
        T3<A, B, C> ty = asT3(y);
        return T3.of(
            getA().apply(tx._1(), ty._1()),
            getB().apply(tx._2(), ty._2()),
            getC().apply(tx._3(), ty._3()));
    }
}
