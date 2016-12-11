package org.highj.function.f1;

import org.derive4j.hkt.__;
import org.highj.function.F1;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asF1;

public interface F1Functor<R> extends Functor<__<F1.µ, R>> {

    @Override
    default  <A, B> F1<R, B> map(Function<A, B> fAB, __<__<F1.µ, R>, A> a) {
        return F1.compose(fAB, asF1(a));
    }
}
