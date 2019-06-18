package org.highj.data.tuple.t4;

import org.derive4j.hkt.__4;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Semigroup;

import static org.highj.Hkt.asT4;

public interface T4Semigroup<A, B, C, D> extends Semigroup<__4<T4.µ, A, B, C, D>> {

    Semigroup<A> getA();

    Semigroup<B> getB();

    Semigroup<C> getC();

    Semigroup<D> getD();

    @Override
    default T4<A, B, C, D> apply(__4<T4.µ, A, B, C, D> x, __4<T4.µ, A, B, C, D> y) {
        T4<A, B, C, D> tx = asT4(x);
        T4<A, B, C, D> ty = asT4(y);
        return T4.of(
            getA().apply(tx._1(), ty._1()),
            getB().apply(tx._2(), ty._2()),
            getC().apply(tx._3(), ty._3()),
            getD().apply(tx._4(), ty._4()));
    }
}
