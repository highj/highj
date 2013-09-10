package org.highj.data.tuple.t4;

import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.tuple.T4;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

public interface T4Bifunctor<S, T> extends Bifunctor<___.µ<____.µ<T4.µ, S>, T>> {
    @Override
    public default <A, B, C, D> T4<S, T, B, D> bimap(Function<A, B> fn1, Function<C, D> fn2, __<___.µ<____.µ<T4.µ, S>, T>, A, C> nestedAC) {
        T4<S, T, A, C> quadruple = T4.narrow(nestedAC);
        return T4.of(quadruple._1(), quadruple._2(), fn1.apply(quadruple._3()), fn2.apply(quadruple._4()));
    }
}
